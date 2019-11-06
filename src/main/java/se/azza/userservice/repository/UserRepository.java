package se.azza.userservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import se.azza.userservice.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	List<User> findByTeamId(Long id);
}
