package pl.pjwstk.woloappapi.security;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.pjwstk.woloappapi.model.Role;
import pl.pjwstk.woloappapi.model.UserEntity;
import pl.pjwstk.woloappapi.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    public static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
    private final UserRepository userRepository;


    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("Email not found"));

        return new User(user.getEmail(), user.getPassword_hash(), mapRoleToAuthorities(user.getRole()));
    }

    private Collection<?extends GrantedAuthority> mapRoleToAuthorities(List<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
    private Collection<? extends GrantedAuthority> mapRoleToAuthorities(Role role) {
        logger.info(role.getName());
        return Collections.singletonList(new SimpleGrantedAuthority(role.getName()));
    }
}
