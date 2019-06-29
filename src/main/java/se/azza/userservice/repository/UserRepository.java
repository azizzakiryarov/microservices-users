package se.azza.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import se.azza.userservice.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
}
