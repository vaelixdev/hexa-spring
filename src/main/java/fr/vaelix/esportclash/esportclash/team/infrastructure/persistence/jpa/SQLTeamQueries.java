package fr.vaelix.esportclash.esportclash.team.infrastructure.persistence.jpa;

import fr.vaelix.esportclash.esportclash.team.application.ports.TeamQueries;
import fr.vaelix.esportclash.esportclash.team.domain.model.Team;
import fr.vaelix.esportclash.esportclash.team.domain.viewmodel.TeamViewModel;
import jakarta.persistence.EntityManager;

public class SQLTeamQueries implements TeamQueries {
    private final EntityManager entityManager;

    public SQLTeamQueries(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public TeamViewModel getTeamById(String id) {
        var query = entityManager.createQuery(
                "SELECT DISTINCT t from Team t " +
                        "JOIN FETCH t.members m " +
                        "JOIN FETCH m.player " +
                        "WHERE t.id = :id", Team.class
        );

        query.setParameter("id", id);

        var result = query.getSingleResult();

        var players = result.getMembers().stream()
                .map(member -> {
                    return new TeamViewModel.TeamMember(
                            member.getId(),
                            member.getPlayer().getId(),
                            member.getPlayer().getName(),
                            member.getRole().name()
                    );
                })
                .toList();

        return new TeamViewModel(
                result.getId(),
                result.getName(),
                players
        );
    }
}
