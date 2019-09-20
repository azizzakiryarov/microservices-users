package se.azza.userservice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.azza.userservice.model.User;
import se.azza.userservice.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User createUser(User user) {
		return userRepository.save(user);
	}

	public Optional<User> getUserById(long userId) {
		return userRepository.findById(userId);
	}

	public void updateUser(User user) {
		userRepository.save(user);
	}

	public void deleteById(Long id) {
		userRepository.deleteById(id);
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
