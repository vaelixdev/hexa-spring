package fr.vaelix.esportclash.esportclash.team.application.usecases;

import an.awesome.pipelinr.Command;
import fr.vaelix.esportclash.esportclash.player.domain.viewmodel.IdResponse;
import fr.vaelix.esportclash.esportclash.team.application.ports.TeamRepository;
import fr.vaelix.esportclash.esportclash.team.domain.model.Team;

import java.util.UUID;

public class CreateTeamCommandHandler implements Command.Handler<CreateTeamCommand, IdResponse> {
    private final TeamRepository teamRepository;

    public CreateTeamCommandHandler(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public IdResponse handle(CreateTeamCommand command) {
        var team = new Team(
                UUID.randomUUID().toString(),
                command.getName()
        );
        teamRepository.save(team);
        return new IdResponse(team.getId());
    }
}
