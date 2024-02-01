package ru.kata.spring.boot_security.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Collections;

@Component
public class AuthProviderImpl implements AuthenticationProvider {
    private final UserService userService;

    @Autowired
    public AuthProviderImpl( UserService userService ){
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        UserDetails userDetails = userService.loadUserByUsername(username);
        String passwodr = authentication.getCredentials().toString();
        if(!passwodr.equals(userDetails.getPassword()))
            throw new BadCredentialsException("Incorrent password");
            return new UsernamePasswordAuthenticationToken(userDetails, passwodr,
                    Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
