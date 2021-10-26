package com.fanduel.oauthpoc;

import com.nimbusds.jose.shaded.json.parser.ParseException;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CustomJwtDecoder implements JwtDecoder {

    @Override
    public Jwt decode(String token) throws JwtException {
        JWT jwt = null;
        try {
            jwt = JWTParser.parse(token);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return createJwt(token, jwt);
    }

    private Jwt createJwt(String token, JWT parsedJwt) {
        try {
            Map<String, Object> headers = new LinkedHashMap<>(parsedJwt.getHeader().toJSONObject());
            Map<String, Object> claims = new HashMap<>();
            claims.putAll(parsedJwt.getJWTClaimsSet().getClaims());
            Date exp = (Date) claims.get("exp");
            Date iat = (Date) claims.get("iat");

            // was complaining about not being Instant
            claims.put("exp", exp.toInstant());
            claims.put("iat", iat.toInstant());
            return Jwt.withTokenValue(token)
                    .headers(h -> h.putAll(headers))
                    .claims(c -> {
                        c.putAll(claims);
                    })
                    .build();
        } catch (Exception ex) {
            if (ex.getCause() instanceof ParseException) {
                throw new JwtException("Malformed payload");
            } else {
                throw new JwtException( ex.getMessage(), ex);
            }
        }
    }
}
