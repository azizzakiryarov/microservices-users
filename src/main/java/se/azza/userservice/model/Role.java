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
import se.azza.userservice.constants.States.userRole;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Enumerated(EnumType.STRING)
	private userRole userRole;
	@Column(name = "roleDescription", nullable = false)
	private String roleDescription;

	public Role() {
	}

	public Role(long id, userRole userRole, String roleDescription) {
		super();
		this.id = id;
		this.userRole = userRole;
		this.roleDescription = roleDescription;
	}

	public Role(userRole userRole, String roleDescription) {
		super();
		this.userRole = userRole;
		this.roleDescription = roleDescription;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Role role = (Role) o;
		return id == role.id && userRole == role.userRole && roleDescription.equals(role.roleDescription);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, userRole, roleDescription);
	}
}