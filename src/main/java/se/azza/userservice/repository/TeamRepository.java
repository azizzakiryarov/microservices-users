package se.azza.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.azza.userservice.model.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {}