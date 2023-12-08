package dev.sabri.securityjwt.service;

import dev.sabri.securityjwt.controller.dto.AuthenticationRequest;
import dev.sabri.securityjwt.controller.dto.AuthenticationResponse;
import dev.sabri.securityjwt.controller.dto.RegisterRequest;
import dev.sabri.securityjwt.exception.BusinessException;
import dev.sabri.securityjwt.mapper.Entity2AcountResponse;
import dev.sabri.securityjwt.repo.UserRepository;
import dev.sabri.securityjwt.model.user.Role;
import dev.sabri.securityjwt.model.user.User;
import dev.sabri.securityjwt.utils.JwtService;
import org.postgresql.util.PSQLException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public record AuthenticationService(UserRepository userRepository,
                                    PasswordEncoder passwordEncoder,
                                    AuthenticationManager authenticationManager) {
    public AuthenticationResponse register(RegisterRequest request) {
        final var user = new User(null,
                request.firstname(),
                request.lastname(),
                request.email(),
                passwordEncoder.encode(request.password()),
                Role.USER);
        try {
            var userResult = userRepository.save(user);
            final var token = JwtService.generateToken(user);
            return new AuthenticationResponse(token, Entity2AcountResponse.INSTANCE.map(userResult));
        }catch (DataIntegrityViolationException e){
            throw new BusinessException(4011, "Account was exists!", 500);
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        final var user = userRepository.findByEmail(request.email()).orElseThrow();
        final var token = JwtService.generateToken(user);
        return new AuthenticationResponse(token, Entity2AcountResponse.INSTANCE.map(user));

    }
}
