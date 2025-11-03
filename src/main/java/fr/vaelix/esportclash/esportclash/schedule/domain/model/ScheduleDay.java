package fr.vaelix.esportclash.esportclash.schedule.domain.model;

import fr.vaelix.esportclash.esportclash.core.domain.model.BaseEntity;
import fr.vaelix.esportclash.esportclash.team.domain.model.Team;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class ScheduleDay extends BaseEntity<ScheduleDay> {
    private LocalDate day;

    private final Map<Moment, Match> matches;

    public ScheduleDay(String id) {
        super(id);
        matches = new EnumMap<>(Moment.class);
    }

    public void organize(Team t1, Team t2, Moment moment) {
        if (matches.containsKey(moment)) {
            throw new IllegalStateException("Moment " + moment + " is already taken");
        }

        var teamAlreadyPlays = matches
                .values()
                .stream()
                .anyMatch(match -> match.hasTeam(t1.getId()) || match.hasTeam(t2.getId()));

        if (teamAlreadyPlays) {
            throw new IllegalStateException("One of the teams is already playing");
        }

        var match = new Match(
                UUID.randomUUID().toString(),
                t1.getId(),
                t2.getId()
        );
        matches.put(moment, match);
    }

    public void cancel() {

    }

    public Optional<Moment> getAt(Moment moment) {
        return matches.containsKey(moment) ? Optional.of(moment) : Optional.empty();
    }

    @Override
    public ScheduleDay deepClone() {
        return null;
    }
}
