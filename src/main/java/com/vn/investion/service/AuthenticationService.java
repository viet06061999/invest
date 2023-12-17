package com.vn.investion.service;

import com.vn.investion.dto.auth.AuthenticationRequest;
import com.vn.investion.dto.auth.AuthenticationResponse;
import com.vn.investion.dto.auth.RegisterRequest;
import com.vn.investion.exception.BusinessException;
import com.vn.investion.mapper.Entity2UserResponse;
import com.vn.investion.model.User;
import com.vn.investion.model.define.Role;
import com.vn.investion.model.define.UserStatus;
import com.vn.investion.repo.UserRepository;
import com.vn.investion.utils.JwtService;
import com.vn.investion.utils.ReferralCodeGenerator;
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
        if (refUser.isEmpty()) {
            throw new BusinessException(4004, "Reference Account not exists!", 404);
        }
        var code = ReferralCodeGenerator.generateReferralCode();
        final var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .phone(request.getPhone())
                .code(code)
                .refId(request.getRefId())
                .availableBalance(0)
                .depositBalance(0)
                .passwd(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .status(UserStatus.PENDING)
                .isLockPoint(Boolean.FALSE)
                .build();

        try {
            var userResult = userRepository.save(user);
            final var token = JwtService.generateToken(user);
            return new AuthenticationResponse(token, Entity2UserResponse.INSTANCE.map(userResult));
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
            return new AuthenticationResponse(token, Entity2UserResponse.INSTANCE.map(user));
        } catch (Exception e) {
            throw new BusinessException(4012, "Login fail!", 500);
        }
    }
}
