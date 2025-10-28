package fr.vaelix.esportclash.esportclash.auth.application.services.jwtservice;

import fr.vaelix.esportclash.esportclash.auth.domain.model.AuthUser;
import fr.vaelix.esportclash.esportclash.auth.domain.model.User;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class ConcreteJwtService implements JwtService {
    private final SecretKey secretKey;
    private final JwtParser parser;
    private final long expiration;

    public ConcreteJwtService(String secret, long expiration) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.parser = Jwts.parser().verifyWith(this.secretKey).build();
        this.expiration = expiration;
    }

    @Override
    public String tokenize(User user) {
        var claims = Jwts.claims()
                .subject(user.getId())
                .add("emailAddress", user.getEmailAddress())
                .build();

        var createdAt = LocalDateTime.now();
        var expiresAt = createdAt.plusMinutes(this.expiration);

        return Jwts.builder()
                .claims(claims)
                .issuedAt(
                        Date.from(createdAt.atZone(ZoneId.systemDefault()).toInstant())
                )
                .expiration(
                        Date.from(expiresAt.atZone(ZoneId.systemDefault()).toInstant())
                )
                .signWith(secretKey)
                .compact();
    }

    @Override
    public AuthUser parse(String token) {
        var claims = parser.parseClaimsJws(token).getBody();

        var id = claims.getSubject();
        var emailAddress = claims.get("emailAddress", String.class);

        return new AuthUser(id, emailAddress);
    }
}
