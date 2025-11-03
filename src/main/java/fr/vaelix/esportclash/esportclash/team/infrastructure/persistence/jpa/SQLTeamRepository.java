package fr.vaelix.esportclash.esportclash.team.infrastructure.persistence.jpa;

import fr.vaelix.esportclash.esportclash.core.infrastructure.persistence.sql.SQLBaseRepository;
import fr.vaelix.esportclash.esportclash.team.application.ports.TeamRepository;
import fr.vaelix.esportclash.esportclash.team.domain.model.Team;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public class SQLTeamRepository extends SQLBaseRepository<Team> implements TeamRepository {
    public SQLTeamRepository(EntityManager em) {
        super(em);
    }

    @Override
    public Optional<Team> findByPlayerId(String playerId) {
        var queries = entityManager
                .createQuery("SELECT t FROM Team t JOIN t.members m WHERE m.playerId = :playerId", Team.class);

        queries.setParameter("playerId", playerId);

        return queries
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public Class<Team> getEntityClass() {
        return Team.class;
    }
}
