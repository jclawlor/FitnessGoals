package dev.jclawlor.FitnessGoals.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import dev.jclawlor.FitnessGoals.TestUtils;
import dev.jclawlor.FitnessGoals.dto.LoginRequestDto;
import dev.jclawlor.FitnessGoals.dto.RegisterRequestDto;
import dev.jclawlor.FitnessGoals.repository.UserRepository;
import dev.jclawlor.FitnessGoals.service.AuthService;
import dev.jclawlor.FitnessGoals.service.UserService;
import org.springframework.transaction.annotation.Transactional;
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AuthControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthService authService;
	
	
	@BeforeEach
	public void setUp() throws Exception {
		userRepository.deleteAll();
	}
	
	
	@Test
	@Transactional
	public void testRegister() throws Exception {
		final String register = mvc.perform( post( "/api/auth/register" ).contentType( "application/json" )
				.content( TestUtils.asJsonString( new RegisterRequestDto("testuser", "testemail@gmail.com", "testpassword") ) ).accept( MediaType.APPLICATION_JSON ) ).andDo( print() )
				.andExpect( status().isCreated() ).andExpect( jsonPath( "$.username" ).value( "testuser" ) )
				.andReturn().getResponse().getContentAsString();
		
		
		assertTrue(register.contains("testuser"));
		assertTrue(userRepository.existsByUsername("testuser"));
	}
	
	@Test
	@Transactional
	public void testLogin() throws Exception {
		final RegisterRequestDto registerDto = new RegisterRequestDto("testuser", "testemail@gmail.com", "testpassword");
		authService.register(registerDto);
		
		
		mvc.perform( post( "/api/auth/login" ).contentType( "application/json" )
				.content( TestUtils.asJsonString( new LoginRequestDto("testuser", "testpassword") ) ).accept( MediaType.APPLICATION_JSON ) ).andDo( print() )
				.andExpect( status().isOk() ).andExpect( jsonPath( "$.username" ).value( "testuser" ) )
				.andReturn().getResponse().getContentAsString();
		
		
		
		
	}
	
	
	
	
	
	

}
