package fr.vaelix.esportclash.esportclash.player;

import fr.vaelix.esportclash.esportclash.core.domain.exceptions.NotFoundException;
import fr.vaelix.esportclash.esportclash.player.application.usecases.RenamePlayerCommand;
import fr.vaelix.esportclash.esportclash.player.application.usecases.RenamePlayerCommandHandler;
import fr.vaelix.esportclash.esportclash.player.domain.model.Player;
import fr.vaelix.esportclash.esportclash.player.infrastructure.persistence.ram.InMemoryPlayerRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RenamePlayerTests {
    private final InMemoryPlayerRepository playerRepository = new InMemoryPlayerRepository();

    private RenamePlayerCommandHandler createHandler() {
        return new RenamePlayerCommandHandler(playerRepository);
    }

    @Test
    void shouldRenamePlayer() {
        var player = new Player("123", "old name");
        playerRepository.save(player);

        var command = new RenamePlayerCommand("123", "new name");

        var commandHandler = createHandler();
        commandHandler.handle(command);

        Player actualPlayer = playerRepository.findById(player.getId()).get();
        Assert.assertEquals(command.getName(), actualPlayer.getName());
    }

    @Test
    void whenPlayerDoesNotExist_shouldThrowNotFound() {
        var command = new RenamePlayerCommand(
                "garbage",
                "new name"
        );

        var commandHandler = createHandler();

        var exception = Assertions.assertThrows(NotFoundException.class, () -> commandHandler.handle(command));

        Assert.assertEquals("Player with the key garbage was not found", exception.getMessage());
    }
}