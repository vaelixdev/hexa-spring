package fr.vaelix.esportclash.esportclash.auth;

import fr.vaelix.esportclash.esportclash.auth.application.services.passwordhasher.BcryptPasswordHasher;
import fr.vaelix.esportclash.esportclash.auth.application.services.passwordhasher.PasswordHasher;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class PasswordHasherTests {
    public PasswordHasher createHasher() {
        return new BcryptPasswordHasher();
    }

    @Test
    public void shouldHashPassword() {
        var hasher = createHasher();
        var clearPassword = "azerty";
        var hashedPassword = hasher.hash(clearPassword);
        Assert.assertTrue(
              hasher.match(clearPassword, hashedPassword)
        );
    }
}