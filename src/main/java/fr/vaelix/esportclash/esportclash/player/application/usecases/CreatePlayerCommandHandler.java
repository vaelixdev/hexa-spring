package fr.vaelix.esportclash.esportclash.player.application.usecases;

import an.awesome.pipelinr.Command;
import fr.vaelix.esportclash.esportclash.player.application.ports.PlayerRepository;
import fr.vaelix.esportclash.esportclash.player.domain.model.Player;
import fr.vaelix.esportclash.esportclash.player.domain.viewmodel.IdResponse;

import java.util.UUID;

public class CreatePlayerCommandHandler implements Command.Handler<CreatePlayerCommand, IdResponse> {
    private final PlayerRepository playerRepository;

    public CreatePlayerCommandHandler(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public IdResponse handle(CreatePlayerCommand command) {
        var player = new Player(UUID.randomUUID().toString(), command.getName());
        this.playerRepository.save(player);
        return new IdResponse(player.getId());
    }
}
