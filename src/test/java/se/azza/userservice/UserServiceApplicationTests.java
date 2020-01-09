package se.azza.userservice;

import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import se.azza.userservice.model.User;
import se.azza.userservice.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceApplicationTests {
	
	static String LOCALHOST = "http://localhost:8081";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	UserService userService;

	@Before
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(userService).build();
	}

	@DisplayName("Test Spring 'getUserById' method")
	@Test
	void testGetUserById() throws Exception {

		UserService userServiceMock = org.mockito.Mockito.mock(UserService.class);
		ResponseEntity<User> userMocked = userServiceMock.getUserById(86);
		Mockito.when(userServiceMock.getUserById(86)).thenReturn(userMocked);
		mockMvc.perform(MockMvcRequestBuilders.get(LOCALHOST + "/users/get/86")
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		Mockito.verify(userServiceMock).getUserById(86);
	}
}
