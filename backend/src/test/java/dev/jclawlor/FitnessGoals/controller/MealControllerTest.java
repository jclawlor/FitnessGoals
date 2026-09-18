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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import dev.jclawlor.FitnessGoals.TestUtils;
import dev.jclawlor.FitnessGoals.dto.MealDto;
import dev.jclawlor.FitnessGoals.enums.TimeOfDay;
import dev.jclawlor.FitnessGoals.repository.MealRepository;
import dev.jclawlor.FitnessGoals.service.MealService;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "testuser", roles = "USER")
public class MealControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private MealRepository mealRepository;
	
	@Autowired
	private MealService mealService;
	
	
	
	
	@BeforeEach
	public void setUp() throws Exception {
		mealRepository.deleteAll();
	}
	
	
	@Test
	public void testGetMeals() throws Exception {
		final String meal = mvc.perform( get( "/api/meals" ) ).andDo( print() ).andExpect( status().isOk() )
                .andReturn().getResponse().getContentAsString();
		
		
		assertTrue(meal.contains("[]"));
		
	}
	
	
	@Test
	@Transactional
	public void testAddMeal() throws Exception {
		final MealDto mealDto = new MealDto("Chicken Salad", 400, 20, 30, 10, TimeOfDay.LUNCH);
		
		mvc.perform( post( "/api/meals" ).contentType( "application/json" )
                .content( TestUtils.asJsonString( mealDto ) ).accept( MediaType.APPLICATION_JSON ) ).andDo( print() )
                .andExpect( status().isOk() ).andExpect( jsonPath( "$.name" ).value( "Chicken Salad" ) )
                .andExpect( jsonPath( "$.calories" ).value( "400" ) )
				.andExpect( jsonPath( "$.carbohydrates" ).value( "20" ) )
				.andExpect( jsonPath( "$.protein" ).value( "30" ) )
				.andExpect( jsonPath( "$.fat" ).value( "10" ) )
				.andExpect( jsonPath( "$.timeOfDay" ).value( TimeOfDay.LUNCH.toString() ) );
		
		
		final String meal = mvc.perform( get( "/api/meals" ) ).andDo( print() ).andExpect( status().isOk() )
				.andReturn().getResponse().getContentAsString();
		
		assertTrue(meal.contains("Chicken Salad"));
		
		
	}
	
	@Test
	@Transactional
	public void testUpdateMeal() throws Exception {
		final MealDto mealDto = new MealDto("Chicken Salad", 400, 20, 30, 10, TimeOfDay.LUNCH);
		final MealDto savedMealDto = mealService.addMeal(mealDto);
		
		savedMealDto.setName("Changed Meal");
		savedMealDto.setCalories(450);
		
		mvc.perform( put( "/api/meals/" + savedMealDto.getId() ).contentType( "application/json" )
				.content( TestUtils.asJsonString( savedMealDto ) ).accept( MediaType.APPLICATION_JSON ) ).andDo( print() )
				.andExpect( status().isOk() ).andExpect( jsonPath( "$.name" ).value( "Changed Meal" ) )
				.andExpect( jsonPath( "$.calories" ).value( "450" ) );
		
		
		final String meal = mvc.perform( get( "/api/meals" ) ).andDo( print() ).andExpect( status().isOk() )
				.andReturn().getResponse().getContentAsString();
		
		assertTrue(meal.contains("Changed Meal"));
		assertFalse(meal.contains("Chicken Salad"));
		
		
	}
	
	@Test
	@Transactional
	public void testDeleteMeal() throws Exception {
		final MealDto mealDto = new MealDto("Chicken Salad", 400, 20, 30, 10, TimeOfDay.LUNCH);
		final MealDto savedMealDto = mealService.addMeal(mealDto);
		
		mvc.perform( delete( "/api/meals/" + savedMealDto.getId() ) ).andDo( print() )
				.andExpect( status().isOk() );
		
		
		final String meal = mvc.perform( get( "/api/meals" ) ).andDo( print() ).andExpect( status().isOk() )
				.andReturn().getResponse().getContentAsString();
		
		assertFalse(meal.contains("Chicken Salad"));
		
		final MealDto mealDto2 = new MealDto("Pasta", 600, 80, 20, 10, TimeOfDay.DINNER);
		
		mvc.perform(delete("/api/meals" + mealDto2.getId())).andDo(print()).andExpect(status().isNotFound());
		
		assertFalse(meal.contains("Pasta"));
		
		
	}
	
	@Test
	@Transactional
	public void testGetMealsByTimeOfDay() throws Exception {
		final MealDto mealDto1 = new MealDto("Chicken Salad", 400, 20, 30, 10, TimeOfDay.LUNCH);
		mealService.addMeal(mealDto1);
		
		final MealDto mealDto2 = new MealDto("Oatmeal", 300, 50, 10, 5, TimeOfDay.BREAKFAST);
		mealService.addMeal(mealDto2);
		
		mvc.perform( get( "/api/meals/timeOfDay/Lunch" ) ).andDo( print() ).andExpect( status().isOk() )
				.andExpect( jsonPath( "$[0].name" ).value( "Chicken Salad" ) )
				.andExpect( jsonPath( "$[0].timeOfDay" ).value( TimeOfDay.LUNCH.toString() ) );
		
		mvc.perform( get( "/api/meals/timeOfDay/Breakfast" ) ).andDo( print() ).andExpect( status().isOk() )
				.andExpect( jsonPath( "$[0].name" ).value( "Oatmeal" ) )
				.andExpect( jsonPath( "$[0].timeOfDay" ).value( TimeOfDay.BREAKFAST.toString() ) );
		
		mvc.perform(get("/api/meals/timeOfDay/Dinner")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string("[]"));
		
		
	}
	
	
	
	
	
	
	
	
	
	

}
