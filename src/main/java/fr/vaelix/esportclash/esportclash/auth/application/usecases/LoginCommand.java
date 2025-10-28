package fr.vaelix.esportclash.esportclash.auth.application.usecases;

import an.awesome.pipelinr.Command;
import fr.vaelix.esportclash.esportclash.auth.domain.viewmodel.LoggedInUserViewModel;

public class LoginCommand implements Command<LoggedInUserViewModel> {
    private String emailAddress;
    private String password;

    public LoginCommand() {

    }

    public LoginCommand(String emailAddress, String password) {
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
}
