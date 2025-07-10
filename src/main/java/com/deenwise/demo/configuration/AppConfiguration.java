package com.deenwise.demo.configuration;
import com.paypal.base.rest.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSecurity
@EnableWebSocketMessageBroker
public class AppConfiguration implements WebSocketMessageBrokerConfigurer
{

   @Value("${paypal-clientId}")
   private String clientId;
   @Value("${paypal-clientKey}")
   private String clientKey;
   @Value("${paypal-mode}")
   private String mode;

   private String[] publicUrls = {
           "/reset", "/login", "/signup", "/register/add", "/password/reset",
           "/user/login","/admin/teacher/add",
           "/aboutus", "/courses", "/pricing", "/home","/css/**", "/js/**","/assets/**"
   };

   @Autowired
   private MyUserDetailsService myUserDetailsService;

   @Bean
   public APIContext apiContext() {
	   return new APIContext(clientId,clientKey,mode);
   }
   
   @Bean
   public ModelMapper getModelMapper() {
	   return new ModelMapper();
   }

   @Bean
   public PasswordEncoder getPasswordEncoder() {
      return new BCryptPasswordEncoder(12);
   }

   @Bean
   public UserDetailsService userDetailsService() {
      return myUserDetailsService;
   }

   @Bean
   public DaoAuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
      authenticationProvider.setPasswordEncoder(getPasswordEncoder());
      authenticationProvider.setUserDetailsService(myUserDetailsService);
      return authenticationProvider;
   }


   @Override
   public void registerStompEndpoints(StompEndpointRegistry registry) {
      registry.addEndpoint("/ws")
              .setAllowedOrigins("*")
              .withSockJS();
   }

   @Override
   public void configureMessageBroker(MessageBrokerRegistry registry) {
      registry.enableSimpleBroker("/topic");
      registry.setApplicationDestinationPrefixes("/app");

   }

   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
         httpSecurity.csrf(csrf -> csrf.disable())
                 .authorizeHttpRequests(requests -> {
                    requests.requestMatchers(publicUrls).permitAll()
                            .requestMatchers("/admin/**").hasRole("ADMIN")
                            .requestMatchers("/teacher/**").hasRole("TEACHER")
                            .requestMatchers("/student/**").hasRole("USER")
                            .anyRequest().authenticated();
                 });

         httpSecurity.formLogin(login -> login.loginPage("/login")
                 .loginProcessingUrl("/user/login")
                 .usernameParameter("email")
                 .failureUrl("/login?error=true")
                 .successHandler(new CustomSuccessAuthenticationHandler())
                 .permitAll());

         httpSecurity.logout(logout ->
                 logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                         .logoutSuccessUrl("/login")
                         .invalidateHttpSession(true)
                         .clearAuthentication(true)
                         .permitAll(true)
         );
         //httpSecurity.oauth2Login(Customizer.withDefaults());

         return httpSecurity.build();
   }
}
