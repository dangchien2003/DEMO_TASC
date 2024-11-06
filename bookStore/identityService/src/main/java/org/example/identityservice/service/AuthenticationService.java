package org.example.identityservice.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.example.identityservice.dto.request.AuthenticationRequest;
import org.example.identityservice.dto.response.AuthenticationResponse;
import org.example.identityservice.entity.Role;
import org.example.identityservice.entity.User;
import org.example.identityservice.exception.AppException;
import org.example.identityservice.exception.ErrorCode;
import org.example.identityservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.UUID;

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


    public AuthenticationResponse customerLoginUsernameAndPassword(AuthenticationRequest request, String userAgent) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);

        if (Objects.isNull(user) ||
                !passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new AppException(ErrorCode.INVALID_USERNAME_PASSWORD);

        validateStatusUser(user);

        return AuthenticationResponse.builder()
                .name(user.getName())
                .accessToken(genAccessToken(user))
                .refreshToken(genRefreshToken(userAgent))
                .expire(timeLiveAccessToken * 60)
                .build();
    }

    void validateStatusUser(User user) {
        switch (user.getStatusCode()) {
            case BLOCK -> throw new AppException(ErrorCode.ACCOUNT_LOCKED);
            case PENDING_VERIFIER -> throw new AppException(ErrorCode.ACCOUNT_PENDING_VERIFY);
            case ACTIVE -> {
            }
            default -> throw new AppException(ErrorCode.NO_ACCESS);
        }
    }

    String genAccessToken(User user) {

        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUid())
                .issuer("book_store")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(timeLiveAccessToken, ChronoUnit.MINUTES).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(secretKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Authen error: ", e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    String genRefreshToken(String userAgent) {

        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(userAgent)
                .issuer("book_store")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(timeLiveRefreshToken, ChronoUnit.MINUTES).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(secretKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Authen error: ", e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        Role role = user.getRole();
        if (!Objects.isNull(user.getRole())) {
            stringJoiner.add("ROLE_" + role.getName());
            user.getRole().getPermissions().forEach(permission ->
                    stringJoiner.add(permission.getName())
            );
        }
        return stringJoiner.toString();
    }
}
