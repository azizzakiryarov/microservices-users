package se.azza.userservice.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import se.azza.userservice.constants.States;
import se.azza.userservice.model.Role;
import se.azza.userservice.model.Team;
import se.azza.userservice.model.User;
import se.azza.userservice.repository.TeamRepository;
import se.azza.userservice.repository.UserRepository;

import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    UserService userService;

    PasswordEncoder passwordEncoder;

    @Mock
    UserService mockUserService;

    @Mock
    UserRepository mockUserRepository;

    @Mock
    TeamRepository mockTeamRepository;

    @Mock
    PasswordEncoder mockPasswordEncoder;

    @Mock
    User mockUser;

    @Mock
    User createdUser;

    @Mock
    Team mockTeam;

    @Mock
    Role mockRole;

    @Before
    public void setUp() {

        mockTeam = new Team(1L, "Alfa", "Alfa team", States.teamState.NEW);
        mockRole = new Role(1L, States.userRole.ADMIN, "Admin");
        mockUser = new User(0L, "John", "Connor", "joco", "123456", States.userState.ACTIVE);

        mockUser.setRole(mockRole);
        mockUser.setTeam(mockTeam);

        userService = new UserService(mockUserRepository, mockTeamRepository, mockPasswordEncoder);
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    public void createUserSuccessfully() {

        ResponseEntity<String> expectedResponseEntity = new ResponseEntity<>(Long.toString(mockUser.getId()), HttpStatus.CREATED);

        //when(mockUserService.isAlreadyExistUserName(mockUser.getUserName())).thenReturn(false);

        when(mockPasswordEncoder.encode(mockUser.getPassword())).thenReturn(passwordEncoder.encode(mockUser.getPassword()));

        ResponseEntity<String> actualResponseEntity = userService.createUser(
                mockUser.getFirstName(),
                mockUser.getLastName(),
                mockUser.getUserName(),
                mockUser.getPassword(),
                mockRole.getUserRole(),
                mockRole.getRoleDescription(),
                mockTeam.getId());

        Assert.assertEquals(expectedResponseEntity, actualResponseEntity);
        verify(mockUserRepository, times(1)).save(any());
    }

    /*
    @Test
    public void createUserWhenUserRoleIsNotContains() {

        mockRole = new Role(1L, States.userRole.IDROTARE, "Idrotare");

        ResponseEntity<String> expectedResponseEntity = ResponseEntity.badRequest().body("Some of " + mockRole.getUserRole() + " not allow to add :) to Username: " + mockUser.getUserName());

        ResponseEntity<String> actualResponseEntity = userService.createUser(
                mockUser.getFirstName(),
                mockUser.getLastName(),
                mockUser.getUserName(),
                mockUser.getPassword(),
                mockRole.getUserRole(),
                mockRole.getRoleDescription(),
                mockTeam.getId());

        Assert.assertEquals(expectedResponseEntity, actualResponseEntity);
        verify(mockUserRepository, times(0)).save(any());
    }
     */

    @Test
    public void updateUser() {
    }

    @Test
    public void deleteById() {
    }

    @Test
    public void getAllUsers() {
    }

    @Test
    public void inLoggning() {
    }

    @Test
    public void isAlreadyExistUserName() {
    }
}