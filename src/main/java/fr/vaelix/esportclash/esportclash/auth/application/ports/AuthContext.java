package fr.vaelix.esportclash.esportclash.auth.application.ports;

import fr.vaelix.esportclash.esportclash.auth.domain.model.AuthUser;

import java.util.Optional;

public interface AuthContext {
    boolean isAuthenticated();
    Optional<AuthUser> getAuthUser();
}