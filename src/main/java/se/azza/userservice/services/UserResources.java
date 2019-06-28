package se.azza.userservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import se.azza.userservice.model.User;
import se.azza.userservice.service.repository.UserServiceRepository;

@RestController
@RequestMapping("/users")
public class UserResources {

	@Autowired
	private UserServiceRepository userServiceRepository;

	@RequestMapping("/add")
	public ResponseEntity<User> addUser(@RequestParam(value = "firstName") String firstName,
			@RequestParam(value = "lastName") String lastName, @RequestParam(value = "userName") String userName,
			@RequestParam(value = "password") String password) {

		User newUser = new User(firstName, lastName, userName, password);
		userServiceRepository.addUser(newUser);
		return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
	}

	@RequestMapping(path = "/get/{id}", method = RequestMethod.GET)
	public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long id) {
		User userId = userServiceRepository.getUserById(id);
		return new ResponseEntity<User>(userId, HttpStatus.OK);
	}
}