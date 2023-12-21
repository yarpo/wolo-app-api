package pl.pjwstk.woloappapi.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.WeakKeyException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pl.pjwstk.woloappapi.config.AppConfig.TokenConfig;

import java.util.Date;
@Service
@AllArgsConstructor
public class TokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    private final TokenConfig tokenConfig;

    public String createToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + tokenConfig.getTokenExpirationMsec());


        return Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(Keys.hmacShaKeyFor(tokenConfig.getTokenSecret().getBytes()), SignatureAlgorithm.HS512)
                .compact();
    }
    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(tokenConfig.getTokenSecret().getBytes()))
                .build()
                .parseClaimsJws(token).getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(tokenConfig.getTokenSecret().getBytes()))
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
