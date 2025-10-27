package fr.vaelix.esportclash.esportclash.player.infrastructure.persistence.ram;

import fr.vaelix.esportclash.esportclash.core.infrastructure.persistence.ram.InMemoryBaseRepository;
import fr.vaelix.esportclash.esportclash.player.application.ports.PlayerRepository;
import fr.vaelix.esportclash.esportclash.player.domain.model.Player;

public class InMemoryPlayerRepository extends InMemoryBaseRepository<Player> implements PlayerRepository {
}
