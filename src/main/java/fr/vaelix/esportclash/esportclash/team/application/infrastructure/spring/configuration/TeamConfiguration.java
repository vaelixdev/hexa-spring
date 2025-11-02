package fr.vaelix.esportclash.esportclash.team.application.infrastructure.spring.configuration;

import fr.vaelix.esportclash.esportclash.team.application.infrastructure.persistence.ram.InMemoryTeamRepository;
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
}
