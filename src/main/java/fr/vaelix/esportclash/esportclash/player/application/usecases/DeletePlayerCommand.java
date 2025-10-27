package fr.vaelix.esportclash.esportclash.player.application.usecases;

import an.awesome.pipelinr.Command;

public class DeletePlayerCommand implements Command<Void> {
    private String id;

    public DeletePlayerCommand(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
