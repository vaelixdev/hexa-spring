package fr.vaelix.esportclash.esportclash.player.infrastructure.spring;

import fr.vaelix.esportclash.esportclash.player.application.ports.PlayerRepository;
import fr.vaelix.esportclash.esportclash.player.infrastructure.persistence.jpa.SQLPlayerRepository;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlayerConfiguration {
    @Bean
    public PlayerRepository playerRepository(EntityManager entityManager) {
        return new SQLPlayerRepository(entityManager);
    }
}