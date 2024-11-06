package org.example.identityservice.service;

import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.example.identityservice.dto.request.AuthenticationRequest;
import org.example.identityservice.dto.response.AuthenticationResponse;
import org.example.identityservice.entity.User;
import org.example.identityservice.exception.AppException;
import org.example.identityservice.exception.ErrorCode;
import org.example.identityservice.repository.UserRepository;
import org.example.identityservice.utils.UserUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @NonFinal
    @Value("${token.time-live-access-token}")
    int timeLiveAccessToken;

    @NonFinal
    @Value("${token.time-live-refresh-token}")
    int timeLiveRefreshToken;

    @NonFinal
    @Value("${token.secret-key}")
    String secretKey;


    public AuthenticationResponse loginUsernameAndPassword(AuthenticationRequest request, String userAgent) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);

        if (Objects.isNull(user) ||
                !passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new AppException(ErrorCode.INVALID_USERNAME_PASSWORD);

        UserUtils.validateStatusUser(user);

        try {
            return UserUtils.createAuthenticationResponse(user, userAgent, secretKey, timeLiveAccessToken, timeLiveRefreshToken);
        } catch (JOSEException e) {
            log.error("Authentication error:", e);
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }
}
