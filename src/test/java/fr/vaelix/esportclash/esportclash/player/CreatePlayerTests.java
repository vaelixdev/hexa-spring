package fr.vaelix.esportclash.esportclash.player;

import fr.vaelix.esportclash.esportclash.player.application.usecases.CreatePlayerCommand;
import fr.vaelix.esportclash.esportclash.player.application.usecases.CreatePlayerCommandHandler;
import fr.vaelix.esportclash.esportclash.player.domain.model.Player;
import fr.vaelix.esportclash.esportclash.player.infrastructure.persistence.ram.InMemoryPlayerRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class CreatePlayerTests  {
    @Test
    void ShouldCreatePlayer() {
        var repository = new InMemoryPlayerRepository();
        var useCase = new CreatePlayerCommandHandler(repository);

        var command = new CreatePlayerCommand("name");
        var result = useCase.handle(command);

        var expectedPlayer = new Player(result.getId(), "name");
        Player actualPlayer = repository.findById(expectedPlayer.getId()).get();

        Assert.assertEquals(expectedPlayer.getName(), actualPlayer.getName());
    }
}
