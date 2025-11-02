package fr.vaelix.esportclash.esportclash.team.usecases;

import fr.vaelix.esportclash.esportclash.core.domain.exceptions.NotFoundException;
import fr.vaelix.esportclash.esportclash.player.domain.model.Player;
import fr.vaelix.esportclash.esportclash.player.infrastructure.persistence.ram.InMemoryPlayerRepository;
import fr.vaelix.esportclash.esportclash.team.application.infrastructure.persistence.ram.InMemoryTeamRepository;
import fr.vaelix.esportclash.esportclash.team.application.usecases.AddPlayerToTeamCommand;
import fr.vaelix.esportclash.esportclash.team.application.usecases.AddPlayerToTeamCommandHandler;
import fr.vaelix.esportclash.esportclash.team.domain.model.Role;
import fr.vaelix.esportclash.esportclash.team.domain.model.Team;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddPlayerToTeamTests {
    InMemoryPlayerRepository playerRepository = new InMemoryPlayerRepository();
    InMemoryTeamRepository teamRepository = new InMemoryTeamRepository();

    Team team = new Team("1", "Team");
    Player player = new Player("1", "Player");

    AddPlayerToTeamCommandHandler createHandler() {
        return new AddPlayerToTeamCommandHandler(
                playerRepository,
                teamRepository
        );
    }

    @BeforeEach
    void setUp() {
        playerRepository.clear();
        teamRepository.clear();

        teamRepository.save(team);
        playerRepository.save(player);
    }

    @Test
    void shouldAddPlayerToTeam() {
        var command = new AddPlayerToTeamCommand(
                player.getId(),
                team.getId(),
                Role.TOP
        );

        var commandHandler = createHandler();
        commandHandler.handle(command);

        var team = teamRepository.findById(command.getTeamId()).get();

        Assert.assertTrue(
                team.hasMember(command.getPlayerId(), Role.TOP)
        );
    }

    @Test
    void whenPlayerDoesNotExist_shouldThrow() {
        var command = new AddPlayerToTeamCommand(
               "garbage",
                team.getId(),
                Role.TOP
        );

        var commandHandler = createHandler();

        var exception = Assertions.assertThrows(
                NotFoundException.class,
                () -> commandHandler.handle(command)
        );

        Assert.assertEquals(
                "Player with the key garbage was not found",
                exception.getMessage()
        );
    }

    @Test
    void whenTeamDoesNotExist_shouldThrow() {
        var command = new AddPlayerToTeamCommand(
                player.getId(),
                "garbage",
                Role.TOP
        );

        var commandHandler = createHandler();

        var exception = Assertions.assertThrows(
                NotFoundException.class,
                () -> commandHandler.handle(command)
        );

        Assert.assertEquals(
                "Team with the key garbage was not found",
                exception.getMessage()
        );
    }
}
