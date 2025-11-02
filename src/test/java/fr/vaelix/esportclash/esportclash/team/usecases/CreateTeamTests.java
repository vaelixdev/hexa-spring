package fr.vaelix.esportclash.esportclash.team.usecases;

import fr.vaelix.esportclash.esportclash.team.application.infrastructure.persistence.ram.InMemoryTeamRepository;
import fr.vaelix.esportclash.esportclash.team.application.usecases.CreateTeamCommand;
import fr.vaelix.esportclash.esportclash.team.application.usecases.CreateTeamCommandHandler;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CreateTeamTests {
    InMemoryTeamRepository teamRepository = new InMemoryTeamRepository();

    CreateTeamCommandHandler createTeamCommandHandler() {
        return new CreateTeamCommandHandler(teamRepository);
    }

    @Test
    void shouldCreateTeam() {
        var command = new CreateTeamCommand("Team");
        var commandHandler = createTeamCommandHandler();

        var response = commandHandler.handle(command);
        var teamQuery = teamRepository.findById(response.getId());

        Assertions.assertTrue(teamQuery.isPresent());
        var team = teamQuery.get();
        Assert.assertEquals("Team", team.getName());
    }
}
