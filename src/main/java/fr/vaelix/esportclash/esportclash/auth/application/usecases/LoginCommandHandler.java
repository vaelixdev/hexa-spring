package fr.vaelix.esportclash.esportclash.auth.application.usecases;

import an.awesome.pipelinr.Command;
import fr.vaelix.esportclash.esportclash.auth.application.ports.UserRepository;
import fr.vaelix.esportclash.esportclash.auth.application.services.jwtservice.JwtService;
import fr.vaelix.esportclash.esportclash.auth.application.services.passwordhasher.PasswordHasher;
import fr.vaelix.esportclash.esportclash.auth.domain.viewmodel.LoggedInUserViewModel;
import fr.vaelix.esportclash.esportclash.core.domain.exceptions.BadRequestException;
import fr.vaelix.esportclash.esportclash.core.domain.exceptions.NotFoundException;

public class LoginCommandHandler implements Command.Handler<LoginCommand, LoggedInUserViewModel> {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordHasher passwordHasher;

    public LoginCommandHandler(UserRepository userRepository,
                               JwtService jwtService,
                               PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public LoggedInUserViewModel handle(LoginCommand loginCommand) {
        var user = this.userRepository
                .findByEmailAddress(loginCommand.getEmailAddress())
                .orElseThrow(() -> new NotFoundException("User"));

        var match = this.passwordHasher.match(
                loginCommand.getPassword(),
                user.getPasswordHash()
        );

        if (!match) {
            throw new BadRequestException("Password does not match");
        }

        var token = this.jwtService.tokenize(user);
        return new LoggedInUserViewModel(user.getId(), user.getEmailAddress(), token);
    }
}
