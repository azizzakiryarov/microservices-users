package se.azza.userservice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import se.azza.userservice.model.User;
import se.azza.userservice.services.UserService;

@SpringBootTest
public class UserServiceApplicationTests {
	
	@Autowired
	UserService userService;
	
	@DisplayName("Test Spring 'getUserById' method")
	@Test
	void testGetUserById() {
		ResponseEntity<User> user1 = userService.getUserById(86);
		ResponseEntity<User> user2 = userService.getUserById(86);
		assertEquals(user1, user2);
	}
}
