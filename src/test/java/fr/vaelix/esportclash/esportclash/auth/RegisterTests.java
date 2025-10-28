package fr.vaelix.esportclash.esportclash.auth;

import fr.vaelix.esportclash.esportclash.auth.application.exception.EmailAddressUnavailableException;
import fr.vaelix.esportclash.esportclash.auth.application.services.passwordhasher.BcryptPasswordHasher;
import fr.vaelix.esportclash.esportclash.auth.application.services.passwordhasher.PasswordHasher;
import fr.vaelix.esportclash.esportclash.auth.application.usecases.RegisterCommand;
import fr.vaelix.esportclash.esportclash.auth.application.usecases.RegisterCommandHandler;
import fr.vaelix.esportclash.esportclash.auth.domain.model.User;
import fr.vaelix.esportclash.esportclash.auth.infrastructure.persistence.ram.InMemoryUserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegisterTests {
    private final InMemoryUserRepository userRepository = new InMemoryUserRepository();
    private final PasswordHasher passwordHasher = new BcryptPasswordHasher();

    public RegisterCommandHandler createHandler() {
        return new RegisterCommandHandler(userRepository, passwordHasher);
    }

    @BeforeEach
    public void setUp() {
        userRepository.clear();
    }

    @Test
    public void shouldRegister() {
        RegisterCommand command = new RegisterCommand(
                "contact@example.fr",
                "password");

        var commandHandler = createHandler();
        var response = commandHandler.handle(command);
        var actualUser = userRepository.findById(response.getId()).get();

        Assert.assertEquals(command.getEmailAddress(), actualUser.getEmailAddress());
        Assert.assertTrue(passwordHasher.match(command.getPassword(), actualUser.getPasswordHash()));
    }

    @Test
    public void whenEmailAddressIsInUse_shouldThrow() {
        var existingUser = new User("123",
                "contact@example.fr",
                "password");

        userRepository.save(existingUser);
        RegisterCommand command = new RegisterCommand(
                existingUser.getEmailAddress(),
                existingUser.getPasswordHash());

        var commandHandler = createHandler();
        var exception = Assert.assertThrows(
                EmailAddressUnavailableException.class,
                () -> commandHandler.handle(command)
        );

        Assert.assertEquals(
                "Email address in already in use",
                exception.getMessage()
        );
     }
}
