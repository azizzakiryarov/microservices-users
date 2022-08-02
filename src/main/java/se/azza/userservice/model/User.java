package se.azza.userservice.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import se.azza.userservice.constants.States.userState;
import se.azza.userservice.constants.States.userRole;

import java.util.Objects;

@Getter
@Setter
@Entity
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "firstName", nullable = false)
    private String firstName;
    @Column(name = "lastName", nullable = false)
    private String lastName;
    @Column(name = "userName", nullable = false, unique = true)
    private String userName;
    @Column(name = "password", nullable = false, unique = true)
    private String password;
    @Enumerated(EnumType.STRING)
    private userState userState;
    @Column(name = "isLogged", nullable = false)
    private boolean isLogged;

    public User() {
    }

    @ManyToOne
    private Team team;

    @OneToOne(cascade = CascadeType.ALL)
    private Role role;

    public User(String firstName, String lastName, String userName, String password, Role role, Team team) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.userState = se.azza.userservice.constants.States.userState.ACTIVE;
        this.role = role;
        this.team = team;
    }

    public User(long id, String firstName, String lastName, String userName, String password, userState userState) {
        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.userState = userState;
    }

    public User(long id, String firstName, String lastName, String userName, String password, userState userState,
                userRole userRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.userState = userState;
        this.role.setUserRole(userRole);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && firstName.equals(user.firstName) && lastName.equals(user.lastName) && userName.equals(user.userName) && password.equals(user.password) && userState == user.userState && team.equals(user.team) && role.equals(user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, userName, password, userState, team, role);
    }
}