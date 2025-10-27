package fr.vaelix.esportclash.esportclash.player.application.usecases;

import an.awesome.pipelinr.Command;
import fr.vaelix.esportclash.esportclash.player.domain.viewmodel.PlayerViewModel;

public class GetPlayerByIdCommand implements Command<PlayerViewModel> {
    private String id;

    public GetPlayerByIdCommand(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
