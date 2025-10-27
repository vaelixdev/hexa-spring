package fr.vaelix.esportclash.esportclash.player.application.usecases;

import an.awesome.pipelinr.Command;
import fr.vaelix.esportclash.esportclash.core.domain.exceptions.NotFoundException;
import fr.vaelix.esportclash.esportclash.player.application.ports.PlayerRepository;
import fr.vaelix.esportclash.esportclash.player.domain.viewmodel.PlayerViewModel;

public class GetPlayerByIdCommandHandler implements Command.Handler<GetPlayerByIdCommand, PlayerViewModel> {
    private final PlayerRepository playerRepository;

    public GetPlayerByIdCommandHandler(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public PlayerViewModel handle(GetPlayerByIdCommand command) {
        var player = playerRepository.findById(command.getId()).orElseThrow(
                () -> new NotFoundException("Player", command.getId())
        );

        return new PlayerViewModel(
                player.getId(),
                player.getName()
        );
    }
}
