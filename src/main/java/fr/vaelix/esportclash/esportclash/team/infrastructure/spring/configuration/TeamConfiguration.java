package fr.vaelix.esportclash.esportclash.team.infrastructure.spring.configuration;

import fr.vaelix.esportclash.esportclash.player.application.ports.PlayerRepository;
import fr.vaelix.esportclash.esportclash.team.application.ports.TeamQueries;
import fr.vaelix.esportclash.esportclash.team.application.usecases.*;
import fr.vaelix.esportclash.esportclash.team.infrastructure.persistence.jpa.SQLTeamQueries;
import fr.vaelix.esportclash.esportclash.team.infrastructure.persistence.jpa.SQLTeamRepository;
import fr.vaelix.esportclash.esportclash.team.infrastructure.persistence.ram.InMemoryTeamRepository;
import fr.vaelix.esportclash.esportclash.team.application.ports.TeamRepository;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TeamConfiguration {
    @Bean
    public TeamRepository teamRepository(EntityManager entityManager) {
        return new SQLTeamRepository(entityManager);
    }

    @Bean
    public CreateTeamCommandHandler createTeamCommandHandler(TeamRepository teamRepository) {
        return new CreateTeamCommandHandler(teamRepository);
    }

    @Bean
    public DeleteTeamCommandHandler deleteTeamCommandHandler(TeamRepository teamRepository) {
        return new DeleteTeamCommandHandler(teamRepository);
    }

    @Bean
    public AddPlayerToTeamCommandHandler addPlayerToTeamCommandHandler(PlayerRepository playerRepository, TeamRepository teamRepository) {
        return new AddPlayerToTeamCommandHandler(playerRepository, teamRepository);
    }

    @Bean
    public RemovePlayerFromTeamCommandHandler removePlayerFromTeamCommandHandler(TeamRepository teamRepository) {
        return new RemovePlayerFromTeamCommandHandler(teamRepository);
    }

    @Bean
    public TeamQueries teamQueries(EntityManager entityManager) {
        return new SQLTeamQueries(entityManager);
    }

    @Bean
    public GetTeamByIdCommandHandler getTeamByIdCommandHandler(TeamQueries teamQueries) {
        return new GetTeamByIdCommandHandler(teamQueries);
    }
}
