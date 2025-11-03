package fr.vaelix.esportclash.esportclash.team.application.ports;

import fr.vaelix.esportclash.esportclash.core.infrastructure.persistence.BaseRepository;
import fr.vaelix.esportclash.esportclash.team.domain.model.Team;

import java.util.Optional;

public interface TeamRepository extends BaseRepository<Team> {
    Optional<Team> findByPlayerId(String playerId);
}
