package fr.vaelix.esportclash.esportclash.schedule.domain;

import fr.vaelix.esportclash.esportclash.schedule.domain.model.Moment;
import fr.vaelix.esportclash.esportclash.schedule.domain.model.ScheduleDay;
import fr.vaelix.esportclash.esportclash.team.domain.model.Role;
import fr.vaelix.esportclash.esportclash.team.domain.model.Team;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class ScheduleDayTests {
    Team createTeam(String id) {
        var name = "Team " + id;
        var team = new Team(id, name);
        team.addMember(
                id + "-1",
                Role.TOP
        );
        team.addMember(
                id + "-2",
                Role.JUNGLE
        );
        team.addMember(
                id + "-3",
                Role.MIDDLE
        );
        team.addMember(
                id + "-4",
                Role.BOTTOM
        );
        team.addMember(
                id + "-5",
                Role.SUPPORT
        );
        return team;
    }


    @Test
    public void shouldOrganize() {
        var t1 = createTeam("t1");
        var t2 = createTeam("t2");
        var moment = Moment.MORNING;

        var scheduleDay = new ScheduleDay(UUID.randomUUID().toString());
        scheduleDay.organize(t1, t2, moment);

        var match = scheduleDay.getAt(Moment.MORNING);
        Assert.assertTrue(match.isPresent());
    }

    @Test
    void whenMomentIsUnaivailable_ShouldThrow() {
        var t1 = createTeam("t1");
        var t2 = createTeam("t2");
        var t3 = createTeam("t3");
        var t4 = createTeam("t4");

        var scheduleDay = new ScheduleDay(UUID.randomUUID().toString());
        scheduleDay.organize(t1, t2, Moment.MORNING);

        var exception = Assert.assertThrows(
                IllegalStateException.class,
                () -> scheduleDay.organize(t3, t4, Moment.MORNING)
        );

        Assert.assertEquals("Moment MORNING is already taken", exception.getMessage());
     }

    @Test
    void whenTeamAlreadyPlaysInTheMorning_shouldFail() {
        var t1 = createTeam("t1");
        var t3 = createTeam("t3");

        var scheduleDay = new ScheduleDay(UUID.randomUUID().toString());
        scheduleDay.organize(t1, t3, Moment.MORNING);

        var exception = Assert.assertThrows(
                IllegalStateException.class,
                () -> scheduleDay.organize(t1, t3, Moment.AFTERNOON)
        );

        Assert.assertEquals("One of the teams is already playing", exception.getMessage());
    }

    @Test
    void whenTeamHas4Members_shouldNotBeComplete() {
        var team = new Team("123", "Team 1");
        team.addMember("1", Role.TOP);
        team.addMember("2", Role.JUNGLE);
        team.addMember("3", Role.MIDDLE);
        team.addMember("4", Role.BOTTOM);

        Assert.assertFalse(team.isComplete());
    }
}
