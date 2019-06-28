package se.azza.userservice.repository;

import se.azza.userservice.model.User;

public interface UserRepository {
	
	User addUser(User user);
	
	User getUserById(long id);
	
	void deleteUserById(long id);
	
	void updateUserById(long id, User user);
}
