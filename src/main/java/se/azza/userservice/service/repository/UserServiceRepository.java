package se.azza.userservice.service.repository;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import se.azza.userservice.model.User;
import se.azza.userservice.repository.UserRepository;

@Service
public class UserServiceRepository implements UserRepository {

	HashMap<Long, User> userRepository = new HashMap<>();
	private final AtomicLong counter = new AtomicLong(0);

	@Override
	public User addUser(User user) {
		long id = counter.incrementAndGet();
		return userRepository.put(id, new User(id, user.getFirstName(), user.getLastName(), user.getUserName(), user.getPassword()));
	}

	@Override
	public User getUserById(long id) {
		return userRepository.get(id);
	}

	@Override
	public void deleteUserById(long id) {
		userRepository.remove(id);
	}

	@Override
	public void updateUserById(long id, User user) {
		User currentUser = userRepository.get(id);
		currentUser.setFirstName(user.getFirstName());
		currentUser.setLastName(user.getLastName());
		currentUser.setUserName(user.getUserName());
		currentUser.setPassword(user.getPassword());
	}
}