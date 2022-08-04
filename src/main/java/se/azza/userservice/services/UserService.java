package se.azza.userservice.services;

import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import se.azza.userservice.constants.States;
import se.azza.userservice.constants.States.userRole;
import se.azza.userservice.model.Issue;
import se.azza.userservice.model.Role;
import se.azza.userservice.model.Team;
import se.azza.userservice.model.User;
import se.azza.userservice.repository.TeamRepository;
import se.azza.userservice.repository.UserRepository;
import se.azza.userservice.resttemplates.RestTemplates;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, TeamRepository teamRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private static final List<States.userRole> userRoles = List.of(
            States.userRole.ADMIN,
            States.userRole.DEVELOPER,
            States.userRole.SCRUMMASTER,
            States.userRole.TEAMMANAGER,
            States.userRole.USER
    );

    public ResponseEntity<String> createUser(String firstName, String lastName, String userName, String password,
                                             userRole userRole, String roleDescription, long teamId) {
        User newUser;
        Role newRole = new Role();
        Team currentTeam = new Team();
        if (userRoles.contains(userRole)) {
            newRole.setUserRole(userRole);
            newRole.setRoleDescription(roleDescription);
        } else {
            return ResponseEntity.badRequest().body("Some of " + userRole + " not allow to add :) to Username: " + userName);
        }
        if (teamRepository.existsById(teamId) || !StringUtils.isEmpty(teamId)) {
            currentTeam.setId(teamId);
        } else {
            return ResponseEntity.badRequest().body("Team with teamId: " + teamId + " doesn't exists!");
        }
        if (!isAlreadyExistUserName(userName)) {
            log.info("Trying to create and save user: {}", userName);
            newUser = new User(firstName, lastName, userName, passwordEncoder.encode(password), newRole, currentTeam);
            userRepository.save(newUser);
            log.info("Successfully created and saved user: {}", userName);
        } else {
            return ResponseEntity.badRequest().body("This username: " + userName + " is already in used :( try again...");
        }
        return new ResponseEntity<>(Long.toString(newUser.getId()), HttpStatus.CREATED);
    }

    public ResponseEntity<User> getUserById(long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
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

    public boolean isUserAlreadyLoggedIn(String userName, String password) {
        return userRepository.findAll().stream().anyMatch(user -> (user.getUserName().equals(userName) &&
                passwordEncoder.encode(user.getPassword()).equals(passwordEncoder.encode(password))) && user.isLogged());
    }

    public boolean isCorrectUsernameAndPassword(String userName, String password) {
        return userRepository.findAll().stream().anyMatch(user -> (user.getUserName().equals(userName) &&
                passwordEncoder.matches(password, user.getPassword())));
    }

    public boolean isAlreadyExistUserName(String userName) {
        return userRepository.findAll().stream().anyMatch(user -> user.getUserName().equals(userName));
    }

    public ResponseEntity<?> loginUser(String userName, String password) {

        if (!userRepository.findByUserName(userName).isPresent()) {
            return ResponseEntity.badRequest().body("User with username: " + userName + "doesn't exists...");
        }

        if (!isCorrectUsernameAndPassword(userName, password)) {
            return ResponseEntity.badRequest().body("Password: " + password + " is not correct...");
        }

        if (!isUserAlreadyLoggedIn(userName, password)) {
            User foundUser = userRepository.findByUserName(userName).get();
            foundUser.setLogged(true);
            userRepository.save(foundUser);
            return ResponseEntity.ok(foundUser.getId());
        } else {
            return ResponseEntity.badRequest().body("Username: " + userName + " is already logged in...");
        }
    }
}