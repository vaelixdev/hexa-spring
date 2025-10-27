package fr.vaelix.esportclash.esportclash.player.infrastructure.persistence.jpa;

import fr.vaelix.esportclash.esportclash.core.infrastructure.persistence.sql.SQLBaseRepository;
import fr.vaelix.esportclash.esportclash.player.application.ports.PlayerRepository;
import fr.vaelix.esportclash.esportclash.player.domain.model.Player;
import jakarta.persistence.EntityManager;

public class SQLPlayerRepository extends SQLBaseRepository<Player> implements PlayerRepository {

    public SQLPlayerRepository(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Player> getEntityClass() {
        return Player.class;
    }
}
