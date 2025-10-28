package fr.vaelix.esportclash.esportclash.auth.infrastructure.spring;

public class LoginDTO {
    private String emailAddress;
    private String password;

    public LoginDTO() {}

    public LoginDTO(String emailAddress, String password) {
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
