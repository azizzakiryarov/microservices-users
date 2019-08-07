package se.azza.userservice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import se.azza.userservice.model.User;
import se.azza.userservice.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class UserResources {

	@Autowired
	private UserRepository userRepository;

	@RequestMapping("/add")
	public ResponseEntity<User> addUser(@RequestParam(value = "firstName") String firstName,
			@RequestParam(value = "lastName") String lastName, @RequestParam(value = "userName") String userName,
			@RequestParam(value = "password") String password) {

		User newUser = new User(firstName, lastName, userName, password);
		userRepository.save(newUser);
		return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
	}

	@GetMapping(path = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long id) {
		Optional<User> userId = userRepository.findById(id);
		return new ResponseEntity<User>(userId.get(), HttpStatus.OK);
	}

	@PutMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> updateUserById(@PathVariable(value = "id") Long id,
			@RequestParam(value = "firstName") String firstName, @RequestParam(value = "lastName") String lastName,
			@RequestParam(value = "userName") String userName, @RequestParam(value = "password") String password) {
		Optional<User> currentUser = userRepository.findById(id);
		User newUser = new User(currentUser.get().getId(), firstName, lastName, userName, password);
		userRepository.save(newUser);
		return new ResponseEntity<User>(newUser, HttpStatus.OK);
	}

	@DeleteMapping(path = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> deleteUserById(@PathVariable(value = "id") Long id) {
		userRepository.deleteById(id);
		return new ResponseEntity<User>(HttpStatus.OK);
	}

	@GetMapping(path = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userRepository.findAll();
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
}