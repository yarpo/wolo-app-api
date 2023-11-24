package pl.pjwstk.woloappapi.service;

import lombok.AllArgsConstructor;
import pl.pjwstk.woloappapi.model.User;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import pl.pjwstk.woloappapi.repository.UserRepository;

@Service
@AllArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = (String) oAuth2User.getAttribute("email");

        User user = userRepository.findByEmail(email);
        if (user != null) {
            throw new RuntimeException("User not found in the repository");
        }


        return oAuth2User;
    }
}
