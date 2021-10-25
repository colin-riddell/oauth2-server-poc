package com.fanduel.oauthpoc;

import com.auth0.jwt.JWT;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;

import java.util.Map;
import java.util.stream.Collectors;

public class CustomJwtDecoder implements JwtDecoder {
    @Override
    public Jwt decode(String token) throws JwtException {
        var decodedToken = JWT.decode(token);
        var userId = decodedToken.getClaim("external_id");

        // Wanted decode to be simple, and not based off public key validation, for now!
        return new Jwt(
                decodedToken.getToken(),
                decodedToken.getIssuedAt().toInstant(),
                decodedToken.getExpiresAt().toInstant(),
                Map.of("typ", decodedToken.getHeaderClaim("typ"), "alg", decodedToken.getHeaderClaim("alg")),
                decodedToken.getClaims().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> (Object) entry.getValue()))
                );
    }
}
