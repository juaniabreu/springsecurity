package com.todocodeacademy.springsecurity.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    @Value("${security.jwt.private.key}")
    private String privateKey;
    @Value("${security.jwt.user.generator}")
    private String userGenerator;

    //CREAR TOKENS
    public String createToken(Authentication authentication) {
        Algorithm algorithm = Algorithm.HMAC256(this.privateKey);
        String username = authentication.getPrincipal().toString();

        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        String jwtToken = JWT.create()
                .withIssuer(this.userGenerator) // el usuario habilitado en crear los tokens del application properties
                .withSubject(username)//al usuario que le voy a crear el token
                .withClaim("authorities", authorities)
                .withIssuedAt(new Date()) //cuando se creo
                .withExpiresAt((new Date(System.currentTimeMillis() + 1000*60*60*10))) // cuanto tiempo va a ser valido el token
                .withJWTId(UUID.randomUUID().toString()) // cada token debe tener un id,te crea una random
                .withNotBefore(new Date(System.currentTimeMillis())) // apartir de cuandoe es valido este token
                .sign(algorithm);

        return jwtToken;
    }

    //HAY QUE DECODIFICAR Y VALIDAR LOS TOKENS
    public DecodedJWT validateToken(String token) {

        try {

        Algorithm algorithm = Algorithm.HMAC256(privateKey);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(this.userGenerator)
                .build();

        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT;

        }
        catch (JWTVerificationException e) {
            throw new JWTVerificationException("Invalid JWT");
        }
    }



    //obtener usuraiod de los tokens
    public String extractUsername(DecodedJWT decodedJWT) {
        return decodedJWT.getSubject();
    }
    //obtener un claim en particular
    public Claim getSpecificClaim (DecodedJWT decodedJWT, String claimName) {
        return decodedJWT.getClaim(claimName);
    }
    //obtener todos los claims
    public Map<String, Claim> getClaims (DecodedJWT decodedJWT) {
        return decodedJWT.getClaims();
    }


}
