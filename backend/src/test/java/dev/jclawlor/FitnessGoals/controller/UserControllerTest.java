package dev.jclawlor.FitnessGoals.controller;

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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import dev.jclawlor.FitnessGoals.entity.User;
import dev.jclawlor.FitnessGoals.repository.UserRepository;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithMockUser(username = "testuser", roles = "USER")
public class UserControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private UserRepository userRepository;
	
	@BeforeEach
	public void setUp() {
	    userRepository.deleteAll();
	    User user = new User();
	    user.setUsername("testuser");
	    user.setPassword("password"); // password not relevant for read
	    user.setEmail("test@example.com");
	    // set roles/other fields as required by entity
	    userRepository.save(user);
	}
	
	@Test
	@Transactional
	public void testGetCurrentUser() throws Exception {
	    mvc.perform(get("/api/users/me").accept(MediaType.APPLICATION_JSON))
	       .andDo(print())
	       .andExpect(status().isOk())
	       .andExpect(jsonPath("$.username").value("testuser"));
	}
	
	@Test
	@Transactional
	public void getUserById() throws Exception {
		Long id = userRepository.findByUsername("testuser").get().getId();
		mvc.perform(get("/api/users/" + id).accept(MediaType.APPLICATION_JSON))
	       .andDo(print())
	       .andExpect(status().isOk())
	       .andExpect(jsonPath("$.username").value("testuser"));
	}
	
	@Test
	@Transactional
	public void testDeleteUser() throws Exception {
		Long id = userRepository.findByUsername("testuser").get().getId();
	    mvc.perform(delete("/api/users/" + id))
	       .andDo(print())
	       .andExpect(status().isNoContent());
	    
	    
	    assertTrue(userRepository.findById(id).isEmpty());
	}
	
	
	

}
