package fr.vaelix.esportclash.esportclash.team.infrastructure.persistence.ram;

import fr.vaelix.esportclash.esportclash.core.infrastructure.persistence.ram.InMemoryBaseRepository;
import fr.vaelix.esportclash.esportclash.team.application.ports.TeamRepository;
import fr.vaelix.esportclash.esportclash.team.domain.model.Team;

public class InMemoryTeamRepository extends InMemoryBaseRepository<Team> implements TeamRepository {}
