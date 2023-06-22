package com.project.visit.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.project.visit.exception.AuthException;
import com.project.visit.exception.ResponseResult;
import com.project.visit.exception.UserException;
import com.project.visit.model.Role;
import com.project.visit.repository.UserRepository;
import com.project.visit.service.AuthService;
import com.project.visit.service.model.AuthModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final Algorithm algorithm = Algorithm.HMAC256("test".getBytes());

    @Override
    public String generateToken(String phone, String password) {
        var user = userRepository.findByPhone(phone).orElseThrow(() -> new UserException(ResponseResult.USER_NOT_FOUND));
        return JWT.create()
                .withClaim("userId", user.getUserId())
                .withExpiresAt(new Date().toInstant().plus(200L, TimeUnit.MINUTES.toChronoUnit()))
                .withClaim("role", user.getRoles().stream().map(Role::toString).toList())
                .sign(algorithm);
    }

    @Override
    public AuthModel checkToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            var claims = verifier.verify(token).getClaims();
            return new AuthModel(claims.get("userId").asString(), claims.get("role").asList(String.class));
        } catch (Exception ex) {
            throw new AuthException(ResponseResult.TOKEN_EXPIRE);
        }
    }
}
