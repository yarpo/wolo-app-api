package pl.pjwstk.woloappapi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pjwstk.woloappapi.model.UserRequestDto;
import pl.pjwstk.woloappapi.repository.UserRepository;
import pl.pjwstk.woloappapi.service.RoleService;
import pl.pjwstk.woloappapi.utils.NotFoundException;
import pl.pjwstk.woloappapi.utils.UserMapper;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationRespons register(UserRequestDto request) {
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("There is an account with that email address: " + request.getEmail());
        }
        var user = userMapper.toUser(request)
                .roles(Collections.singletonList(roleService.getRoleByName("USER")))
                .password(passwordEncoder.encode(request.getPassword()))
                .isPeselVerified(false)
                .isAgreementSigned(false)
                .build();
        var savedUser = userRepository.save(user);
        var jwt = jwtService.generateToken(user);
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
        return AuthenticationRespons.builder()
                .token(jwt)
                .build();
    }
}
