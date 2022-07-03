package ulas1.backend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

//Deze klasse ondersteunt het JwtRequestFilter
// in het genereren van tokens en het bepalen
// of een token valide is
@Service
public class JwtService {
    private final static String SECRET_KEY = "ZwarteMagie"; //Zeer passende Secret Key

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    //Deze methode genereert een token aan de hand van UserDetails.
    //De parameter "currentTime" is toegevoegd om makkelijker unit tests te kunnen uitvoeren.
    //Bij gebruik in productie moet de parameter "currentTime" altijd op System.currentTimeMillis() staan
    public String generateToken(UserDetails userDetails, long currentTime){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername(), currentTime);
    }

    private String createToken(Map<String, Object> claims, String subject, long currentTime){
        long validPeriod = 1000 * 60 * 60 * 24; // 1 dag in milliseconden
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(currentTime + validPeriod))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) &&
                !isTokenExpired(token);
    }
}
