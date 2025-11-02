package fr.vaelix.esportclash.esportclash.team.application.usecases;

import an.awesome.pipelinr.Command;
import fr.vaelix.esportclash.esportclash.core.domain.exceptions.NotFoundException;
import fr.vaelix.esportclash.esportclash.team.application.ports.TeamRepository;

public class DeleteTeamCommandHandler implements Command.Handler<DeleteTeamCommand, Void> {
    private final TeamRepository teamRepository;

    public DeleteTeamCommandHandler(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public Void handle(DeleteTeamCommand command) {
        var team = teamRepository.findById(command.getId())
                .orElseThrow(() -> new NotFoundException("Team"));

        teamRepository.delete(team);

        return null;
    }
}
