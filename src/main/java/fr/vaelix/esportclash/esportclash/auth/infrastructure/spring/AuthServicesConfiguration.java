package fr.vaelix.esportclash.esportclash.auth.infrastructure.spring;

import fr.vaelix.esportclash.esportclash.auth.application.services.passwordhasher.BcryptPasswordHasher;
import fr.vaelix.esportclash.esportclash.auth.application.services.passwordhasher.PasswordHasher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthServicesConfiguration {
    @Bean
    public PasswordHasher passwordHasher() {
        return new BcryptPasswordHasher();
    }
}
