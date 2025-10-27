package fr.vaelix.esportclash.esportclash.player;

import fr.vaelix.esportclash.esportclash.core.domain.exceptions.NotFoundException;
import fr.vaelix.esportclash.esportclash.player.application.usecases.DeletePlayerCommand;
import fr.vaelix.esportclash.esportclash.player.application.usecases.DeletePlayerCommandHandler;
import fr.vaelix.esportclash.esportclash.player.domain.model.Player;
import fr.vaelix.esportclash.esportclash.player.infrastructure.persistence.ram.InMemoryPlayerRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DeletePlayerTests {
    private final InMemoryPlayerRepository playerRepository = new InMemoryPlayerRepository();

    private DeletePlayerCommandHandler createHandler() {
        return new DeletePlayerCommandHandler(playerRepository);
    }

    @Test
    void shouldDeletePlayer() {
         var player = new Player("123", "name");
         playerRepository.save(player);

         var command = new DeletePlayerCommand(
                 "123"
         );

         var commandHandler = createHandler();
         commandHandler.handle(command);

         var playerQuery = playerRepository.findById(player.getId());
         Assert.assertTrue(playerQuery.isEmpty());
    }

    @Test
    void whenPlayerDoesNotExist_shouldThrowNotFound() {
        var command = new DeletePlayerCommand("garbage");
        var commandHandler = createHandler();

        var exception = Assertions.assertThrows(NotFoundException.class, () -> commandHandler.handle(command));

        Assertions.assertEquals("Player with the key garbage was not found", exception.getMessage());
    }
}
