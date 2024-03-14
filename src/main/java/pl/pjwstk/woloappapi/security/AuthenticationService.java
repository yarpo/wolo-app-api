package pl.pjwstk.woloappapi.security;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.pjwstk.woloappapi.model.User;
import pl.pjwstk.woloappapi.model.UserRequestDto;
import pl.pjwstk.woloappapi.repository.UserRepository;
import pl.pjwstk.woloappapi.service.RoleService;
import pl.pjwstk.woloappapi.utils.NotFoundException;
import pl.pjwstk.woloappapi.utils.UserMapper;

import java.util.Collections;

import static pl.pjwstk.woloappapi.security.TokenType.BEARER;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationRespons register(UserRequestDto request) {
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("There is an account with that email address: " + request.getEmail());
        }
        User user = userMapper.toUser(request)
                .roles(Collections.singletonList(roleService.getRoleByName("USER")))
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        User savedUser = userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        saveToken(savedUser, jwt);
        return AuthenticationRespons.builder()
                .token(jwt)
                .build();
    }

    public AuthenticationRespons authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException("User email not found!"));
        var jwt = jwtService.generateToken(user);
        revokeUserTokens(user);
        saveToken(user, jwt);
        return AuthenticationRespons.builder()
                .token(jwt)
                .build();
    }

    private void revokeUserTokens(User user){
        var tokens = tokenRepository.findAllValidTokensByUser(user.getId());
        if(tokens.isEmpty()){
            return;
        }
        tokens.forEach(t -> t.setExpired(true));
        tokenRepository.saveAll(tokens);
    }
    private void saveToken(User user, String jwt) {
        var token = Token.builder()
                .user(user)
                .token(jwt)
                .tokenType(BEARER)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }
}
