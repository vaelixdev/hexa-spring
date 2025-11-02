package fr.vaelix.esportclash.esportclash.team.domain;

import fr.vaelix.esportclash.esportclash.team.domain.model.Role;
import fr.vaelix.esportclash.esportclash.team.domain.model.Team;
import org.junit.Assert;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class TeamTests {
    @Nested
    class AddMember {
        @Test
        void shouldJoinTeam() {
            var team = new Team("123", "Team 1");
            team.addMember("player1", Role.TOP);

            Assert.assertTrue(team.hasMember("player1", Role.TOP));
        }

        @Test
        void whenThePlayerIsAlreadyInTheTeam_shouldThrow() {
            var team = new Team("123", "Team 1");
            team.addMember("player1", Role.TOP);

            var exception = Assert.assertThrows(
                    IllegalArgumentException.class,
                    () -> team.addMember("player1", Role.TOP)
            );

            Assert.assertEquals(exception.getMessage(), "Player already in team");
        }

        @Test
        void whenTheRoleIsAlreadyTaken_shouldThrow() {
            var team = new Team("123", "Team 1");
            team.addMember("player1", Role.TOP);

            var exception = Assert.assertThrows(
                    IllegalArgumentException.class,
                    () -> team.addMember("player2", Role.TOP)
            );

            Assert.assertEquals(exception.getMessage(), "Role is already taken");
        }
    }

    @Nested
    class RemoveMember {
        @Test
        void shouldRemoveMember() {
            var team = new Team("123", "Team 1");
            team.addMember("player1", Role.TOP);
            team.removeMember("player1");

            Assert.assertFalse(team.hasMember("player1", Role.TOP));
        }

        @Test
        void whenPlayerIsNotInTheTeam_shouldThrow() {
            var team = new Team("123", "Team 1");

            var exception = Assert.assertThrows(
                    IllegalArgumentException.class,
                    () -> team.removeMember("player1")
            );

            Assert.assertEquals(exception.getMessage(), "Player not in team");
        }
    }
}
