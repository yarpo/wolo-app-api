package pl.pjwstk.woloappapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pl.pjwstk.woloappapi.configs.TokenConfig;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final TokenConfig tokenConfig;

    public String extractUsername(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        return claimsResolver.apply(extractAllClaims(token));
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(tokenConfig.getTokenSecret().getBytes());
    }

    public String buildToken(Map<String, Object> extraClaims, UserDetails user, long expiration){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+expiration))
                .signWith(getKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails){
        return buildToken(extraClaims, userDetails, tokenConfig.getAccessTokenExpiration());
    }

    public String generateRefreshToken(
            UserDetails userDetails){
        return buildToken(new HashMap<>(), userDetails, tokenConfig.getRefreshTokenExpiration());
    }

    public boolean isTokenValid(String token, UserDetails user){
        final String username = extractUsername(token);
        return (username.equals(user.getUsername()) && ! isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}
