package fr.vaelix.esportclash.esportclash.auth.infrastructure.spring;

import fr.vaelix.esportclash.esportclash.auth.application.ports.UserRepository;
import fr.vaelix.esportclash.esportclash.auth.infrastructure.persistence.ram.InMemoryUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthAdaptersConfiguration {
    @Bean
    public UserRepository userRepository() {
        return new InMemoryUserRepository();
    }
}
