package fr.vaelix.esportclash.esportclash.team.infrastructure.persistence.ram;

import fr.vaelix.esportclash.esportclash.core.infrastructure.persistence.ram.InMemoryBaseRepository;
import fr.vaelix.esportclash.esportclash.team.application.ports.TeamRepository;
import fr.vaelix.esportclash.esportclash.team.domain.model.Team;

import java.util.Optional;

public class InMemoryTeamRepository extends InMemoryBaseRepository<Team> implements TeamRepository {
    @Override
    public Optional<Team> findByPlayerId(String playerId) {
        return entities.values().stream()
                .filter(team -> team.getMembers().stream().anyMatch(member -> member.getPlayerId().equals(playerId)))
                .findFirst();
    }
}
