package fr.vaelix.esportclash.esportclash.auth.infrastructure.spring;

import fr.vaelix.esportclash.esportclash.auth.application.ports.AuthContext;
import fr.vaelix.esportclash.esportclash.auth.application.ports.UserRepository;
import fr.vaelix.esportclash.esportclash.auth.infrastructure.persistence.jpa.SQLUserAccessor;
import fr.vaelix.esportclash.esportclash.auth.infrastructure.persistence.jpa.SQLUserRepository;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthAdaptersConfiguration {
    @Bean
    public UserRepository userRepository(EntityManager entityManager, SQLUserAccessor accessor) {
        return new SQLUserRepository(entityManager, accessor);
    }

    @Bean
    public AuthContext getAuthContext() {
        return new SpringAuthContext();
    }
}
