package fr.vaelix.esportclash.esportclash.auth.domain.model;

import fr.vaelix.esportclash.esportclash.core.domain.model.BaseEntity;

public class User extends BaseEntity {
    private String emailAddress;
    private String passwordHash;

    public User() {}

    public User(String id, String emailAddress, String password) {
        super(id);
        this.emailAddress = emailAddress;
        this.passwordHash = password;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
}
