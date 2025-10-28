package fr.vaelix.esportclash.esportclash.auth;

import fr.vaelix.esportclash.esportclash.auth.application.services.jwtservice.ConcreteJwtService;
import fr.vaelix.esportclash.esportclash.auth.application.services.jwtservice.JwtService;
import fr.vaelix.esportclash.esportclash.auth.application.services.passwordhasher.BcryptPasswordHasher;
import fr.vaelix.esportclash.esportclash.auth.application.services.passwordhasher.PasswordHasher;
import fr.vaelix.esportclash.esportclash.auth.application.usecases.LoginCommand;
import fr.vaelix.esportclash.esportclash.auth.application.usecases.LoginCommandHandler;
import fr.vaelix.esportclash.esportclash.auth.domain.model.User;
import fr.vaelix.esportclash.esportclash.auth.infrastructure.persistence.ram.InMemoryUserRepository;
import fr.vaelix.esportclash.esportclash.core.domain.exceptions.BadRequestException;
import fr.vaelix.esportclash.esportclash.core.domain.exceptions.NotFoundException;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class LoginTests {
    private final InMemoryUserRepository repository = new InMemoryUserRepository();
    private final JwtService jwtService = new ConcreteJwtService(
            "supersecretpleasechangeitsomethingbetter",
            60
    );
    private final PasswordHasher passwordHasher = new BcryptPasswordHasher();
    private final User finalUser = new User(
            "123",
            "contact@example.com",
            new BcryptPasswordHasher().hash("azerty")
    );

    LoginCommandHandler createHandler() {
        return new LoginCommandHandler(repository, jwtService, passwordHasher);
    }

    @BeforeEach
    public void setup() {
        repository.clear();
        repository.save(finalUser);
    }

    @Nested
    class HappyPath {
        @Test
        void shouldReturnTheUser() {
            var command = new LoginCommand("contact@example.com", "azerty");
            var commandHandler = createHandler();
            var result = commandHandler.handle(command);

            Assert.assertEquals(result.getId(), finalUser.getId());
            Assert.assertEquals(finalUser.getEmailAddress(), result.getEmailAddress());

            var authenticatedUser = jwtService.parse(result.getToken());
            Assert.assertEquals(finalUser.getId(), authenticatedUser.getId());
            Assert.assertEquals(finalUser.getEmailAddress(), authenticatedUser.getEmailAddress());
        }
    }

    @Nested
    class Scenario_TheEmailAddressIsIncorrect {
        @Test
        void shouldThrowNotFound() {
            var command = new LoginCommand("contact@incorrect.fr", "azerty");
            var commandHandler = createHandler();

            var exception = Assert.assertThrows(NotFoundException.class, () -> commandHandler.handle(command));

            Assert.assertEquals(exception.getMessage(), "User was not found");
        }
    }

    @Nested
    class Scenario_ThePasswordIsIncorrect {
        @Test
        void shouldThrowBadRequest() {
            var command = new LoginCommand("contact@example.com", "not the correct password");
            var commandHandler = createHandler();

            var exception = Assert.assertThrows(BadRequestException.class, () -> commandHandler.handle(command));
            Assert.assertEquals(exception.getMessage(), "Password does not match");
        }
    }
}