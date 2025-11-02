package fr.vaelix.esportclash.esportclash.team.infrastructure.spring.configuration;

import fr.vaelix.esportclash.esportclash.player.application.ports.PlayerRepository;
import fr.vaelix.esportclash.esportclash.team.application.usecases.AddPlayerToTeamCommandHandler;
import fr.vaelix.esportclash.esportclash.team.application.usecases.DeleteTeamCommandHandler;
import fr.vaelix.esportclash.esportclash.team.application.usecases.RemovePlayerFromTeamCommandHandler;
import fr.vaelix.esportclash.esportclash.team.infrastructure.persistence.ram.InMemoryTeamRepository;
import fr.vaelix.esportclash.esportclash.team.application.ports.TeamRepository;
import fr.vaelix.esportclash.esportclash.team.application.usecases.CreateTeamCommandHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TeamConfiguration {
    @Bean
    public TeamRepository teamRepository() {
        return new InMemoryTeamRepository();
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
}
