package fr.vaelix.esportclash.esportclash.auth.application.services.jwtservice;

import fr.vaelix.esportclash.esportclash.auth.domain.model.AuthUser;
import fr.vaelix.esportclash.esportclash.auth.domain.model.User;

public interface JwtService {
    String tokenize(User user);
    AuthUser parse(String token);
}