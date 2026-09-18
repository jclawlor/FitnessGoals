package dev.jclawlor.FitnessGoals.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;

import dev.jclawlor.FitnessGoals.dto.AuthResponseDto;
import dev.jclawlor.FitnessGoals.dto.LoginRequestDto;
import dev.jclawlor.FitnessGoals.dto.RegisterRequestDto;
import dev.jclawlor.FitnessGoals.repository.UserRepository;
import dev.jclawlor.FitnessGoals.security.JwtUtil;
import org.springframework.transaction.annotation.Transactional;
@SpringBootTest
public class AuthServiceTest {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthService authService;
	
	@Autowired
    private JwtUtil jwtUtil;
	
	@BeforeEach
	public void setUp() throws Exception {
		userRepository.deleteAll();
	}
	
	@Test
	@Transactional
	public void testRegister() throws Exception {
		final RegisterRequestDto registerDto = new RegisterRequestDto("testuser1", "testemail1", "testpassword1");
		final RegisterRequestDto registerDto2 = new RegisterRequestDto("testuser2", "testemail1", "testpassword2");
		
		AuthResponseDto authResponse = authService.register(registerDto);
		assertAll("AuthResponse contents",
				() -> assertEquals("testuser1", authResponse.getUsername()),
				() -> assertEquals("testemail1", authResponse.getEmail()));
		
		
		try {
			authService.register(registerDto);
		} catch (RuntimeException e) {
			assertEquals("Username already taken", e.getMessage());
		}
		
		try {
			authService.register(registerDto2);
		} catch (RuntimeException e) {
			assertEquals("Email already registered", e.getMessage());
		}
		
		
	}
	
	@Test
	@Transactional
	public void testLogin() throws Exception {
		final RegisterRequestDto registerDto = new RegisterRequestDto("testuser1", "testemail1", "testpassword1");

		
		authService.register(registerDto);
		
		final LoginRequestDto loginDto = new LoginRequestDto("testuser1", "testpassword1");
		final LoginRequestDto loginDto2 = new LoginRequestDto("testuser2", "testpassword1");
		AuthResponseDto authResponse = authService.login(loginDto);
		assertAll("AuthResponse contents",
				() -> assertEquals("testuser1", authResponse.getUsername()),
				() -> assertEquals("testemail1", authResponse.getEmail()),
				() -> assertNotNull(authResponse.getToken()),
				() -> jwtUtil.isTokenValid(authResponse.getToken()));
		
		try {
			authService.login(new LoginRequestDto("testuser1", "wrongpassword"));
		} catch(BadCredentialsException e) {
			assertEquals("Invalid username or password", e.getMessage());
		}
		
		try {
			authService.login(loginDto2);
		} catch(BadCredentialsException e) {
			assertEquals("Invalid username or password", e.getMessage());
		}
		
		
		
		
		
	}
	
	

}
