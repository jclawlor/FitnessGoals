package dev.jclawlor.FitnessGoals.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import dev.jclawlor.FitnessGoals.dto.RegisterRequestDto;
import dev.jclawlor.FitnessGoals.dto.UserDto;
import dev.jclawlor.FitnessGoals.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class UserServiceTest {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthService authService;
	
	@BeforeEach
	public void setUp() throws Exception {
		userRepository.deleteAll();
	}
	
	
	@Test
	@Transactional
	public void testGetUserByUsername() {
		final RegisterRequestDto registerDto = new RegisterRequestDto("testuser1", "testemail1", "testpassword1");
		authService.register(registerDto);
		
		UserDto userDto = new UserDto("testuser1", "testemail1", "testpassword1");
		UserDto fetchedUserDto = userService.getUserByUsername("testuser1");
		assertAll("UserDto contents",
				() -> assertEquals(userDto.getUsername(), fetchedUserDto.getUsername()),
				() -> assertEquals(userDto.getEmail(), fetchedUserDto.getEmail()));
		
		
	}
	
	@Test
	@Transactional
	public void testDeleteUser() {
		final RegisterRequestDto registerDto = new RegisterRequestDto("testuser1", "testemail1", "testpassword1");
		authService.register(registerDto);
		
		UserDto fetchedUserDto = userService.getUserByUsername("testuser1");
		userService.deleteUser(fetchedUserDto.getId());
		
		try {
			userService.getUserByUsername("testuser1");
		} catch (Exception e) {
			assertEquals("Could not find user with this username", e.getMessage());
		}
		
		assertEquals(0, userRepository.count());
		
	}
	
	@Test
	@Transactional
	public void testGetUserById() {
		final RegisterRequestDto registerDto = new RegisterRequestDto("testuser1", "testemail1", "testpassword1");
		authService.register(registerDto);
		
		UserDto fetchedUserDto = userService.getUserByUsername("testuser1");
		UserDto fetchedUserById = userService.getUserById(fetchedUserDto.getId());
		
		assertAll("UserDto contents",
				() -> assertEquals(fetchedUserDto.getUsername(), fetchedUserById.getUsername()),
				() -> assertEquals(fetchedUserDto.getEmail(), fetchedUserById.getEmail()));
	}
	
	

}
