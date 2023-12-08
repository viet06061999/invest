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
import dev.sabri.securityjwt.utils.ReferralCodeGenerator;
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
        var refUser = userRepository.findByCode(request.getRefId());
        if(refUser.isEmpty()){
            throw new BusinessException(4004, "Reference Account not exists!", 404);
        }
        var code = ReferralCodeGenerator.generateReferralCode();
        final var user = new User(null,
                request.getFirstname(),
                request.getLastname(),
                request.getPhone(),
                code,
                request.getRefId(),
                0,
                passwordEncoder.encode(request.getPassword()),
                Role.USER);
        try {
            var userResult = userRepository.save(user);
            final var token = JwtService.generateToken(user);
            return new AuthenticationResponse(token, Entity2AcountResponse.INSTANCE.map(userResult));
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException(4011, "Account was exists!", 500);
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.phone(),
                            request.password()
                    )
            );
            final var user = userRepository.findByPhone(request.phone()).orElseThrow();
            final var token = JwtService.generateToken(user);
            return new AuthenticationResponse(token, Entity2AcountResponse.INSTANCE.map(user));
        } catch (Exception e) {
            throw new BusinessException(4012, "Login fail!", 500);
        }
    }
}
