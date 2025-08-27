package org.deenwise.app.apis.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.deenwise.app.apis.dto.UserDTO;
import org.deenwise.app.apis.exception.UserNotFoundException;
import org.deenwise.app.apis.model.UserModel;
import org.deenwise.app.apis.repository.UserRepository;
import org.deenwise.app.apis.request.RefreshTokenRequest;
import org.deenwise.app.apis.response.AuthResponse;
import org.deenwise.app.apis.tokens.AccessToken;
import org.deenwise.app.apis.tokens.RefreshToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Function;
@Service
public class JwtService {

    private String secret_key;
    @Autowired
    private UserRepository userRepository;
    private SecretKey key = null;
    public SecretKey getKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            if(key == null)
                key = keyGenerator.generateKey();

            secret_key = Base64.getEncoder().encodeToString(key.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return key;
    }

    public AccessToken generateAccessToken(UserDTO userDTO) {

        Map<String,String> claims = new HashMap<>();
        Date expiry_time = new Date(System.currentTimeMillis() * 1000 * 60 * 5);
        String token =  Jwts.builder()
                .subject(userDTO.getEmail())
                .expiration(expiry_time) //  last for 5 minutes
                .issuedAt(new Date(System.currentTimeMillis()))
                .claims(claims)
                .signWith(getKey())
                .compact();
        return AccessToken.builder()
                .token(token)
                .expiry_time(expiry_time.toString())
                .build();
    }

    public RefreshToken generateRefreshToken(UserDTO userDTO) {
        Map<String,String> claims = new HashMap<>();
        Date expiry_time = new Date(System.currentTimeMillis() * 1000 * 60 * 60 * 24 * 7);

        String token = Jwts.builder()
                .subject(userDTO.getEmail())
                .expiration(expiry_time) //  last for 7 days
                .issuedAt(new Date(System.currentTimeMillis()))
                .claims(claims)
                .signWith(getKey())
                .compact();

        return RefreshToken.builder()
                .token(token)
                .expiry_time(expiry_time.toString())
                .build();
    }

    public <T>T extractClaim(String token, Function<Claims,T> claimsResolver) {
        Claims claims = extractClaims(token);
        return claimsResolver.apply(claims);
    }

    public SecretKey getSecretKey() {
        byte[] data = Base64.getDecoder().decode(secret_key);
        return Keys.hmacShaKeyFor(data);
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String email = extractEmail(token);
        System.out.println(email);
        System.out.println(userDetails.getUsername());
        Date expirationDate = getExpiration(token);
        return (Objects.equals(email,userDetails.getUsername()) && new Date(System.currentTimeMillis()).before(expirationDate));
    }

    public Date getExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

    public String extractEmail(String token) {
        return extractClaim(token,Claims::getSubject);
    }

    public AuthResponse revalidateAccessToken(RefreshTokenRequest refreshTokenRequest) {
        Date expirationDate = getExpiration(refreshTokenRequest.getRefresh_token()); //get refresh_token expiration
        AuthResponse authResponse = null;
        if(expirationDate.before(new Date(System.currentTimeMillis()))) {
            if(refreshTokenRequest.getEmail().equals(extractEmail(refreshTokenRequest.getRefresh_token()))) {
                UserModel userModel = userRepository.findByEmail(refreshTokenRequest.getEmail())
                        .orElseThrow(() -> new UserNotFoundException("User Data Not Found.."));

                UserDTO userDTO = UserDTO.builder()
                        .id(userModel.getId())
                        .fullname(userModel.getFullname())
                        .email(userModel.getEmail())
                        .password(userModel.getPassword())
                        .build();
                AccessToken accessToken = generateAccessToken(userDTO);

               authResponse = AuthResponse.builder()
                        .access_token(accessToken.getToken())
                        .expiry_time(accessToken.getExpiry_time())
                        .refresh_token(refreshTokenRequest.getRefresh_token())
                        .refresh_expiry_time(expirationDate.toString())
                        .email(refreshTokenRequest.getEmail())
                        .build();
            }
        }
        return authResponse;
    }
}
