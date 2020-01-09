package se.azza.userservice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import se.azza.userservice.constants.States;
import se.azza.userservice.constants.States.userRole;
import se.azza.userservice.model.Issue;
import se.azza.userservice.model.Role;
import se.azza.userservice.model.Team;
import se.azza.userservice.model.User;
import se.azza.userservice.repository.UserRepository;
import se.azza.userservice.resttemplates.RestTemplates;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public ResponseEntity<String> createUser(String firstName, String lastName, String userName, String password,
			userRole userRole, String roleDescription, long teamId) {
		Role newRole = new Role();
		if (States.userRole.ADMIN.equals(userRole) || States.userRole.DEVELOPER.equals(userRole)
				|| States.userRole.SCRUMMASTER.equals(userRole) || States.userRole.TEAMMANAGER.equals(userRole)
				|| States.userRole.USER.equals(userRole)) {
			newRole.setUserRole(userRole);
			newRole.setRoleDescription(roleDescription);
		} else {
			return ResponseEntity.badRequest()
					.body("Some of " + userRole + " not allow to add :) to Username: " + userName);
		}
		Team currentTeam = new Team();
		if (!StringUtils.isEmpty(teamId)) {
			currentTeam.setId(teamId);
		} else {
			return ResponseEntity.badRequest().body("Something is wrong with teamId: " + teamId);
		}
		if (!userRepository.findByUserName(userName).getUserName().equals(userName)) {
			User newUser = new User(firstName, lastName, userName, password, newRole, currentTeam);
			userRepository.save(newUser);
		} else {
			return ResponseEntity.badRequest()
					.body("This username: " + userName + " is already in used :( try again...");
		}
		return new ResponseEntity<String>(HttpStatus.CREATED);
	}

	public ResponseEntity<User> getUserById(long userId) {
		Optional<User> user = userRepository.findById(userId);
		if (!user.isEmpty()) {
			return new ResponseEntity<User>(user.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	public void updateUser(User user) {
		userRepository.save(user);
	}

	public ResponseEntity<String> deleteById(Long id, RestTemplate restTemplate) {
		List<Issue> issuesForUser = RestTemplates.getIssuesForUser(restTemplate, id);
		if (issuesForUser.isEmpty()) {
			userRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return ResponseEntity.badRequest()
				.body("You need to delete all issues first... then you available to delete user");
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User inLoggning(String userName, String password) {
		return userRepository.findAll().stream()
				.filter(user -> user.getUserName().equals(userName) && user.getPassword().equals(password)).findAny()
				.orElse(null);
	}

}