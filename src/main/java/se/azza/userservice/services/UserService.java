package se.azza.userservice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import se.azza.userservice.model.Issue;
import se.azza.userservice.model.User;
import se.azza.userservice.repository.UserRepository;
import se.azza.userservice.resttemplates.RestTemplates;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User createUser(User user) {
		return userRepository.save(user);
	}

	public ResponseEntity<User> getUserById(long userId) {
		Optional<User> user = userRepository.findById(userId);
		if(!user.isEmpty()) {
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