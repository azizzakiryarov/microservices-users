package se.azza.userservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import se.azza.userservice.constants.States.issueState;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "issues")
public class Issue {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "comment", nullable = false)
	private String comment;
	@Enumerated(EnumType.STRING)
	private issueState issueState;

	@ManyToOne
	private User user;

	public Issue() {
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Issue issue = (Issue) o;
		return id == issue.id && comment.equals(issue.comment) && issueState == issue.issueState && user.equals(issue.user);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, comment, issueState, user);
	}


}