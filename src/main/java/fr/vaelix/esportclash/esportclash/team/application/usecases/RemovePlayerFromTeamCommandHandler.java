package fr.vaelix.esportclash.esportclash.team.application.usecases;

import an.awesome.pipelinr.Command;
import fr.vaelix.esportclash.esportclash.core.domain.exceptions.NotFoundException;
import fr.vaelix.esportclash.esportclash.team.application.ports.TeamRepository;

public class RemovePlayerFromTeamCommandHandler implements Command.Handler<RemovePlayerFromTeamCommand, Void> {
    private TeamRepository teamRepository;

    public RemovePlayerFromTeamCommandHandler(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public Void handle(RemovePlayerFromTeamCommand command) {
        var team = teamRepository
                .findById(command.getTeamId())
                .orElseThrow(() -> new NotFoundException("Team"));

        team.removeMember(command.getPlayerId());

        teamRepository.save(team);

        return null;
    }
}
