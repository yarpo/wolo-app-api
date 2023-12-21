package pl.pjwstk.woloappapi.security;

import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.WeakKeyException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;
import pl.pjwstk.woloappapi.config.AppProperties;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
@AllArgsConstructor
public class TokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    private final AppProperties appProperties;

    public String createToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + appProperties.getAuth().getTokenExpirationMsec());


        return Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(Keys.hmacShaKeyFor(appProperties.getAuth().getTokenSecret().getBytes()), SignatureAlgorithm.HS512)
                .compact();
    }
    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(appProperties.getAuth().getTokenSecret().getBytes()))
                .build()
                .parseClaimsJws(token).getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(appProperties.getAuth().getTokenSecret().getBytes()))
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        } catch (WeakKeyException ex) {
            logger.error("key byte array length is less than 256 bits");
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
           logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
         logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
          logger.error("JWT claims string is empty.");
        }
        return false;
    }
}
