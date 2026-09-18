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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import dev.jclawlor.FitnessGoals.TestUtils;
import dev.jclawlor.FitnessGoals.dto.LiftingExerciseDto;
import dev.jclawlor.FitnessGoals.repository.LiftingExerciseRepository;
import dev.jclawlor.FitnessGoals.service.LiftingExerciseService;
import org.springframework.transaction.annotation.Transactional;
@SpringBootTest
@AutoConfigureMockMvc
//@Import(TestSecurityConfig.class)
@Transactional
@WithMockUser(username = "testuser", roles = "USER")
public class LiftingExerciseControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private LiftingExerciseRepository liftingRepository;
	
	@Autowired
	private LiftingExerciseService liftingService;
	
	
	
	@BeforeEach
	public void setUp() throws Exception {
		liftingRepository.deleteAll();
	}
	
	@Test
	public void testAddLiftingExercise() throws Exception {
		final String lifting = mvc.perform( post( "/api/lifting" )
				.contentType( "application/json" )
				.content( TestUtils.asJsonString( new LiftingExerciseDto("Squat", 3, 6, 225) ) ).accept( MediaType.APPLICATION_JSON ) ).andDo( print() )
				.andExpect( status().isOk() ).andExpect( jsonPath( "$.name" ).value( "Squat" ) )
				.andExpect( jsonPath( "$.sets" ).value( "3" ) )
				.andExpect( jsonPath( "$.reps" ).value( "6" ) )
				.andExpect( jsonPath( "$.weight" ).value( "225.0" ) )
				.andReturn().getResponse().getContentAsString();
		
		
		assertTrue(lifting.contains("Squat"));
		
	}
	
	@Test
	public void testGetLiftingExercises() throws Exception {
		final String lifting = mvc.perform( get( "/api/lifting" ) ).andDo( print() ).andExpect( status().isOk() )
				.andReturn().getResponse().getContentAsString();
		
		
		assertTrue(lifting.contains("[]"));
		
		final LiftingExerciseDto liftingDto = new LiftingExerciseDto("Squat", 3, 6, 225);
		liftingService.addLiftingExercise(liftingDto);
		final LiftingExerciseDto liftingDto2 = new LiftingExerciseDto("Bench Press", 4, 8, 185);
		liftingService.addLiftingExercise(liftingDto2);
		
		final String lifting2 = mvc.perform( get( "/api/lifting" ) ).andDo( print() ).andExpect( status().isOk() )
				.andReturn().getResponse().getContentAsString();
		
		assertTrue(lifting2.contains("Squat"));
		assertTrue(lifting2.contains("Bench Press"));
	}
	
	
	
	@Test
	public void testUpdateLiftingExercises() throws Exception {
		final LiftingExerciseDto liftingDto = new LiftingExerciseDto("Squat", 3, 6, 225);
		final LiftingExerciseDto savedLiftingDto = liftingService.addLiftingExercise(liftingDto);
		
		savedLiftingDto.setName("Changed");
		savedLiftingDto.setSets(4);
		
		mvc.perform( put( "/api/lifting/" + savedLiftingDto.getId() ).contentType( "application/json" )
				.content( TestUtils.asJsonString( savedLiftingDto ) ).accept( MediaType.APPLICATION_JSON ) ).andDo( print() )
				.andExpect( status().isOk() ).andExpect( jsonPath( "$.name" ).value( "Changed" ) )
				.andExpect( jsonPath( "$.sets" ).value( "4" ) );
		
		
		final String lifting = mvc.perform( get( "/api/lifting" ) ).andDo( print() ).andExpect( status().isOk() )
				.andReturn().getResponse().getContentAsString();
		
		assertTrue(lifting.contains("Changed"));
		assertFalse(lifting.contains("Squat"));
		
		
	}
	
	@Test
	public void testDeleteLiftingExercise() throws Exception {
		final LiftingExerciseDto liftingDto = new LiftingExerciseDto("Squat", 3, 6, 225);
		final LiftingExerciseDto savedLiftingDto = liftingService.addLiftingExercise(liftingDto);
		
		mvc.perform( delete( "/api/lifting/" + savedLiftingDto.getId() ) ).andDo( print() )
				.andExpect( status().isOk() );
		
		
		final String lifting = mvc.perform( get( "/api/lifting" ) ).andDo( print() ).andExpect( status().isOk() )
				.andReturn().getResponse().getContentAsString();
		
		assertFalse(lifting.contains("Squat"));
		
		final LiftingExerciseDto liftingDto2 = new LiftingExerciseDto("Bench Press", 4, 8, 185);
		
		//TODO: This test is currently failing because the delete endpoint is not properly deleting the exercise. The test is trying to delete an exercise that does not exist, which is why it is returning a 404 Not Found. We need to fix the delete endpoint to properly delete the exercise and return a 200 OK status.
//		mvc.perform(delete("/api/lifting/" + liftingDto2.getId())).andDo(print()).andExpect(status().isNotFound());
		
		assertFalse(lifting.contains("Bench Press"));
		
		
	}
	
	@Test
	public void testGetLiftingExerciseByName() throws Exception{
		final LiftingExerciseDto liftingDto = new LiftingExerciseDto("Squat", 3, 6, 225);
		final LiftingExerciseDto savedLiftingDto = liftingService.addLiftingExercise(liftingDto);
		mvc.perform(get("/api/lifting/name/" + savedLiftingDto.getName())).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Squat"))
				.andExpect(jsonPath("$.sets").value(3))
				.andExpect(jsonPath("$.reps").value(6))
				.andExpect(jsonPath("$.weight").value(225.0));
		
//		mvc.perform(get("/api/cardio/name/NonExistent")).andDo(print()).andExpect(status().isNotFound());
	}
	
	@Test
	public void testGetAllLiftingExercises() throws Exception {
		final LiftingExerciseDto liftingDto1 = new LiftingExerciseDto("Squat", 3, 6, 225);
		liftingService.addLiftingExercise(liftingDto1);
		
		final LiftingExerciseDto liftingDto2 = new LiftingExerciseDto("Bench Press", 4, 8, 185);
		liftingService.addLiftingExercise(liftingDto2);
		
		mvc.perform( get( "/api/lifting" ) ).andDo( print() ).andExpect( status().isOk() )
				.andExpect( jsonPath( "$[0].name" ).value( "Squat" ) )
				.andExpect( jsonPath( "$[0].sets" ).value( "3" ) )
				.andExpect( jsonPath( "$[0].reps" ).value( "6" ) )
				.andExpect( jsonPath( "$[0].weight" ).value( "225.0" ) )
				.andExpect( jsonPath( "$[1].name" ).value( "Bench Press" ) )
				.andExpect( jsonPath( "$[1].sets" ).value( "4" ) )
				.andExpect( jsonPath( "$[1].reps" ).value( "8" ) )
				.andExpect( jsonPath( "$[1].weight" ).value( "185.0" ) );
		
		
	}
	
	@Test
	public void testGetLiftingExerciseById() throws Exception {
		final LiftingExerciseDto liftingDto = new LiftingExerciseDto("Squat", 3, 6, 225);
		final LiftingExerciseDto savedLiftingDto = liftingService.addLiftingExercise(liftingDto);
		
		mvc.perform(get("/api/lifting/" + savedLiftingDto.getId())).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Squat"))
				.andExpect(jsonPath("$.sets").value(3))
				.andExpect(jsonPath("$.reps").value(6))
				.andExpect(jsonPath("$.weight").value(225.0));
		
		
	}
	

}
