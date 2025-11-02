package fr.vaelix.esportclash.esportclash.team.usecases;

import fr.vaelix.esportclash.esportclash.core.domain.exceptions.NotFoundException;
import fr.vaelix.esportclash.esportclash.player.domain.model.Player;
import fr.vaelix.esportclash.esportclash.player.infrastructure.persistence.ram.InMemoryPlayerRepository;
import fr.vaelix.esportclash.esportclash.team.application.infrastructure.persistence.ram.InMemoryTeamRepository;
import fr.vaelix.esportclash.esportclash.team.application.usecases.AddPlayerToTeamCommand;
import fr.vaelix.esportclash.esportclash.team.application.usecases.AddPlayerToTeamCommandHandler;
import fr.vaelix.esportclash.esportclash.team.application.usecases.RemovePlayerFromTeamCommand;
import fr.vaelix.esportclash.esportclash.team.application.usecases.RemovePlayerFromTeamCommandHandler;
import fr.vaelix.esportclash.esportclash.team.domain.model.Role;
import fr.vaelix.esportclash.esportclash.team.domain.model.Team;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RemovePlayerFromTeamTests {
    InMemoryTeamRepository teamRepository = new InMemoryTeamRepository();

    Team team;
    String playerId = "playerId";
    Role role = Role.TOP;

    RemovePlayerFromTeamCommandHandler createHandler() {
        return new RemovePlayerFromTeamCommandHandler(
                teamRepository
        );
    }

    @BeforeEach
    void setUp() {
        teamRepository.clear();
        team = new Team("1", "Team 1");
        team.addMember(playerId, role);
        teamRepository.save(team);
    }

    @Test
    void shouldRemovePlayerFromTeam() {
        var command = new RemovePlayerFromTeamCommand(
                playerId,
                team.getId()
        );

        var commandHandler = createHandler();
        commandHandler.handle(command);

        var team = teamRepository.findById(command.getTeamId()).get();

        Assert.assertFalse(
                team.hasMember(playerId, role)
        );
    }

    @Test
    void whenTeamDoesNotExist_shouldThrow() {
        var command = new RemovePlayerFromTeamCommand(
                playerId,
                "garbage"
        );

        var commandHandler = createHandler();

        var exception = Assertions.assertThrows(
                NotFoundException.class,
                () -> commandHandler.handle(command)
        );

        Assert.assertEquals(
                "Team was not found",
                exception.getMessage()
        );
    }
}
