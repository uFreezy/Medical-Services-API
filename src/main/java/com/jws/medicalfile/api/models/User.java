package com.jws.medicalfile.api.models;

import com.jws.medicalfile.api.models.base.Person;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "users")
public class User extends Person {
    private String username;
    private String password;
    @Transient
    private String passwordConfirm;
    @ManyToOne
    private Role role;

    public User() {
    }

    public User(String firstName, String lastName, String username, String password, String passwordConfirm, Role role) {
        super(firstName, lastName);
        this.username = username;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + super.getId() +
                ", firstName='" + super.getFirstName() + '\'' +
                ", lastName='" + super.getLastName() + '\'' +
                ", email='" + username + '\'' +
                ", password='" + "*********" + '\'' +
                ", role=" + role +
                '}';
    }
}

