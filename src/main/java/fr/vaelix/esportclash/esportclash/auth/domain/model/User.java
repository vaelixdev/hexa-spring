package fr.vaelix.esportclash.esportclash.auth.domain.model;

import fr.vaelix.esportclash.esportclash.core.domain.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User extends BaseEntity<User> {
    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "password_hash")
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

    @Override
    public User deepClone() {
        return new User(this.id, this.emailAddress, this.passwordHash);
    }
}
