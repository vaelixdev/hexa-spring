package fr.vaelix.esportclash.esportclash.auth;

import fr.vaelix.esportclash.esportclash.auth.application.services.jwtservice.ConcreteJwtService;
import fr.vaelix.esportclash.esportclash.auth.domain.model.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class JwtServiceTests {
    @Test
    void shouldTokenizeTheUser() {
        var jwtService = new ConcreteJwtService("sooper-sekret_please_do_not_share", 60);
        var user = new User("123", "contact@example.com", "azerty");

        var token = jwtService.tokenize(user);
        var authUser = jwtService.parse(token);

        Assert.assertNotNull(authUser);
        Assert.assertEquals(user.getEmailAddress(), authUser.getEmailAddress());
        Assert.assertEquals(user.getId(), authUser.getId());
    }
}
