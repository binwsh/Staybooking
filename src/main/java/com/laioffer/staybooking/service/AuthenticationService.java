package com.laioffer.staybooking.service;

import com.laioffer.staybooking.entity.Authority;
import com.laioffer.staybooking.entity.Token;
import com.laioffer.staybooking.entity.User;
import com.laioffer.staybooking.entity.UserRole;
import com.laioffer.staybooking.exception.UserNotExistException;
import com.laioffer.staybooking.repository.AuthorityRepository;
import com.laioffer.staybooking.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private AuthenticationManager authenticationManager;
    private AuthorityRepository authorityRepository;
    private JwtUtil jwtUtil;

    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager, JwtUtil jwtUtil, AuthorityRepository authorityRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.authorityRepository = authorityRepository;
    }

    public Token authenticate(User user, UserRole role) throws UserNotExistException {
        // verify username and password
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),
                    user.getPassword()));
        } catch (AuthenticationException exception) {
            throw new UserNotExistException("User doesn't exist");
        }

        // verify authority
        Authority authority = authorityRepository.findById(user.getUsername()).orElse(null);
        if (!authority.getAuthority().equals(role.name())) {
            throw new UserNotExistException("User doesn't exist");
        }

        // generate token
        return new Token(jwtUtil.generateToken(user.getUsername()));
    }
}

// check user -> generate token -> return token
// check user -> return not exist exception
