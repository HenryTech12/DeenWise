package org.deenwise.app.apis.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.deenwise.app.apis.dto.UserDTO;
import org.deenwise.app.apis.exception.UserNotFoundException;
import org.deenwise.app.apis.filters.AuthFilter;
import org.deenwise.app.apis.filters.JwtFilter;
import org.deenwise.app.apis.model.UserModel;
import org.deenwise.app.apis.repository.UserRepository;
import org.deenwise.app.apis.response.AuthResponse;
import org.deenwise.app.apis.response.AuthErrorResponse;
import org.deenwise.app.apis.service.JwtService;
import org.deenwise.app.apis.tokens.AccessToken;
import org.deenwise.app.apis.tokens.RefreshToken;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Configuration
@EnableWebSecurity
public class AppConfiguration {

    private ObjectMapper objectMapper = new ObjectMapper();

    private String[] publicUrls = {
            "/deen/api/user/login",
            "/deen/api/user/create",
            "/v3/api-docs/**",    // OpenAPI JSON
            "/swagger-ui.html",   // Swagger UI HTML entrypoint
            "/swagger-ui/**",     // Swagger UI resources (JS, CSS)
            "/webjars/**",        // (optional, legacy)
            "/actuator/**"
    };

    @Autowired
    private JwtService jwtService;


    @Autowired
    private JwtFilter jwtFilter;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(myUserDetailsService);
        authenticationProvider.setPasswordEncoder(getPasswordEncoder());

        return authenticationProvider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return myUserDetailsService;
    }

    @Bean
    public AuthFilter authFilter(AuthenticationManager authenticationManager) {
        AuthFilter authFilter = new AuthFilter();
        authFilter.setAuthenticationManager(authenticationManager);
        authFilter.setFilterProcessesUrl("/deen/api/user/login");
        authFilter.setAuthenticationSuccessHandler(((request, response, authentication) -> {
            response.setStatus(HttpServletResponse.SC_OK);


            UserModel userModel = userRepository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new UserNotFoundException("User Data Not Found.."));

            UserDTO userDTO = UserDTO.builder()
                    .id(userModel.getId())
                    .fullname(userModel.getFullname())
                    .email(userModel.getEmail())
                    .password(userModel.getPassword())
                    .build();

            AccessToken accessToken = jwtService.generateAccessToken(userDTO);
            RefreshToken refreshToken = jwtService.generateRefreshToken(userDTO);

            AuthResponse authResponse = AuthResponse.builder()
                    .access_token(accessToken.getToken())
                    .expiry_time(accessToken.getExpiry_time())
                    .refresh_token(refreshToken.getToken())
                    .refresh_expiry_time(refreshToken.getExpiry_time())
                    .email(userDTO.getEmail())
                    .userRole(userModel.getUserRole().name())
                    .build();

            response.getWriter().write(objectMapper.writeValueAsString(authResponse));
        }));

        authFilter.setAuthenticationFailureHandler(((request, response, exception) -> {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            AuthErrorResponse authErrorResponse =
                    AuthErrorResponse.builder()
                            .message(Objects.requireNonNullElse(exception.getMessage(),"Bad Credentials"))
                            .timeStamp(LocalDateTime.now().toString())
                            .authenticated(false)
                            .build();

            response.getWriter().write(objectMapper.writeValueAsString(authErrorResponse));
        }));

        return authFilter;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173","http://localhost:5500","https://expo-nent.vercel.app")); // frontend port
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true); // important for cookies or auth headers

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }


    @Bean
    public AuthenticationManager getAuthManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {

        httpSecurity.csrf(csrf -> csrf.disable())
                .cors(cors -> corsConfigurationSource())
                .authorizeHttpRequests(requests ->
                        requests.requestMatchers(publicUrls).permitAll()
                                .anyRequest().authenticated())
                .addFilterAt(authFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
