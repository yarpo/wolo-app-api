package pl.pjwstk.woloappapi.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;
    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        final String header = request.getHeader(AUTHORIZATION);
        if(header != null || !header.startsWith("Bearer ")){
            return;
        }
        final String jwtToken = header.substring(7);
        var storedToken = tokenRepository.findByToken(jwtToken);
        if(storedToken.isPresent()){
            storedToken.get().setExpired(true);
            tokenRepository.save(storedToken.get());
        }
    }
}