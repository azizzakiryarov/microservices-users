package se.azza.userservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import se.azza.userservice.constants.States.teamState;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "teamName", nullable = false)
    private String teamName;
    @Column(name = "teamDescription", nullable = false)
    private String teamDescription;
    @Enumerated(EnumType.STRING)
    private teamState teamState;

    public Team() {
    }

    public Team(long id, String teamName, String teamDescription, teamState teamState) {
        super();
        this.id = id;
        this.teamName = teamName;
        this.teamDescription = teamDescription;
        this.teamState = teamState;
    }

    public Team(String teamName, String teamDescription, teamState teamState) {
        super();
        this.teamName = teamName;
        this.teamDescription = teamDescription;
        this.teamState = teamState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return id == team.id && teamName.equals(team.teamName) && teamDescription.equals(team.teamDescription) && teamState == team.teamState;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, teamName, teamDescription, teamState);
    }
}