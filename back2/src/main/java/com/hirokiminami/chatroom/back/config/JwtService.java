//package com.hirokiminami.chatroom.back2.config;
//
//import com.hirokiminami.chatroom.back2.model.User;
//import io.jsonwebtoken.Claims;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.oauth2.jwt.JwtClaimsSet;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.security.oauth2.jwt.JwtEncoder;
//import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
//import org.springframework.stereotype.Service;
//
//import java.time.Instant;
//import java.time.temporal.ChronoUnit;
//import java.util.Objects;
//import java.util.function.Function;
//
//@Service
//public class JwtService {
//
//    private final JwtEncoder encoder;
//
//    private final JwtDecoder decoder;
//
//    public JwtService(JwtEncoder encoder, JwtDecoder decoder) {
//        this.encoder = encoder;
//        this.decoder = decoder;
//    }
//
//    public String extractUsername(String token) {
//        return this.decoder.decode(token).getSubject();
//    }
//
//    public String generateToken(Authentication authentication) {
//        Instant now = Instant.now();
//        JwtClaimsSet claims = JwtClaimsSet.builder()
//                .issuer("self")
//                .issuedAt(now)
//                .expiresAt(now.plus(1, ChronoUnit.DAYS))
//                .subject(authentication.getName())
//                .build();
//        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
//    }
//
//    public boolean isTokenValid(String token, UserDetails userDetails) {
//        String username = extractUsername(token);
//        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
//    }
//
//    private boolean isTokenExpired(String token) {
//        return Instant.now().isAfter(Objects.requireNonNull(this.decoder.decode(token).getExpiresAt()));
//    }
//
////    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
////        Claims claims = extractAllClaims(token);
////        return claimsResolver.apply(claims);
////    }
////
////    public Claims extractAllClaims(String token) {
////        return this.decoder.decode(token).getSubject();
////    }
//}
