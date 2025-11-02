package fr.vaelix.esportclash.esportclash.team.usecases;

import fr.vaelix.esportclash.esportclash.core.domain.exceptions.NotFoundException;
import fr.vaelix.esportclash.esportclash.team.application.infrastructure.persistence.ram.InMemoryTeamRepository;
import fr.vaelix.esportclash.esportclash.team.application.usecases.DeleteTeamCommand;
import fr.vaelix.esportclash.esportclash.team.application.usecases.DeleteTeamCommandHandler;
import fr.vaelix.esportclash.esportclash.team.domain.model.Team;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DeleteTeamTests {
    InMemoryTeamRepository teamRepository = new InMemoryTeamRepository();

    Team team = new Team("1", "Team");

    DeleteTeamCommandHandler deleteTeamCommandHandler() {
        return new DeleteTeamCommandHandler(teamRepository);
    }

    @BeforeEach
    void setuUp() {
        teamRepository.clear();
        teamRepository.save(team);
    }

    @Test
    void shouldDeleteTeam() {
        var command = new DeleteTeamCommand(team.getId());
        var commandHandler = deleteTeamCommandHandler();

        commandHandler.handle(command);

        var teamQuery = teamRepository.findById(team.getId());

        Assert.assertFalse(teamQuery.isPresent());
    }

    @Test
    void whenTeamDoesNotExist_shouldFail() {
        var command = new DeleteTeamCommand("garbage");
        var commandHandler = deleteTeamCommandHandler();

        var exception = Assert.assertThrows(
                NotFoundException.class,
                () -> commandHandler.handle(command)
        );

        Assert.assertEquals("Team was not found", exception.getMessage());
    }
}
