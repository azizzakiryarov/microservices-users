package se.azza.userservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import se.azza.userservice.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	List<User> findByTeamId(Long id);
	
	@Query("select u from User u where u.userName = :userName")
	Optional<User> findByUserName(@Param("userName") String userName);
}