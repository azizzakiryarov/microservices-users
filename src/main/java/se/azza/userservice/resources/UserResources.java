package se.azza.userservice.resources;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import se.azza.userservice.constants.States.userState;
import se.azza.userservice.constants.States.userRole;
import se.azza.userservice.model.User;
import se.azza.userservice.repository.UserRepository;
import se.azza.userservice.services.UserService;

@RestController
@RequestMapping("/users")
public class UserResources {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping("/add")
	public ResponseEntity<String> addUser(@RequestParam(value = "firstName") String firstName,
			@RequestParam(value = "lastName") String lastName, @RequestParam(value = "userName") String userName,
			@RequestParam(value = "password") String password, @RequestParam(value = "userRole") userRole userRole,
			@RequestParam(value = "roleDescription") String roleDescription,
			@RequestParam(value = "teamId") long teamId) {
		return userService.createUser(firstName, lastName, userName, password, userRole, roleDescription, teamId);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long id) {
		return userService.getUserById(id);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PutMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> updateUserById(@PathVariable(value = "id") Long id,
			@RequestParam(value = "firstName") String firstName, @RequestParam(value = "lastName") String lastName,
			@RequestParam(value = "userName") String userName, @RequestParam(value = "password") String password,
			@RequestParam(value = "userState") userState userState) {
		Optional<User> currentUser = userRepository.findById(id);
		User newUser = new User(currentUser.get().getId(), firstName, lastName, userName, password, userState,
				currentUser.get().getRole().getUserRole());
		userService.updateUser(newUser);
		return new ResponseEntity<User>(newUser, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@DeleteMapping(path = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteUserById(@PathVariable(value = "id") Long id) {
		return userService.deleteById(id, restTemplate);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userService.getAllUsers();
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> inLoggning(@RequestParam(value = "userName") String userName, @RequestParam(value = "password") String password) {
		User user = userService.inLoggning(userName, password);
		user.setLogged(true);
		userRepository.save(user);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "/logout/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> Logout(@PathVariable(value = "id") Long id) {
		Optional<User> user = userRepository.findById(id);
		user.get().setLogged(false);
		userRepository.save(user.get());
		return new ResponseEntity<User>(user.get(), HttpStatus.OK);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "/getAllUsersFor/{teamId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<User>> getTeamsUsers(@PathVariable(value = "teamId") long teamId) {
		List<User> users = userRepository.findByTeamId(teamId);
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "/getTeamId/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> getTeamId(@PathVariable(value = "userId") long userId) {
		Optional<User> user = userRepository.findById(userId);
		return new ResponseEntity<Long>(user.get().getTeam().getId(), HttpStatus.OK);
	}
}