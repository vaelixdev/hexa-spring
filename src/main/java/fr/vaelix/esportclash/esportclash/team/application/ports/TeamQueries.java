package fr.vaelix.esportclash.esportclash.team.application.ports;

import fr.vaelix.esportclash.esportclash.team.domain.viewmodel.TeamViewModel;

public interface TeamQueries {
    TeamViewModel getTeamById(String id);
}
