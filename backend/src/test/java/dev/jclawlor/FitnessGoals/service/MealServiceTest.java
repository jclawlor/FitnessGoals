package dev.jclawlor.FitnessGoals.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import dev.jclawlor.FitnessGoals.dto.MealDto;
import dev.jclawlor.FitnessGoals.enums.TimeOfDay;
import dev.jclawlor.FitnessGoals.repository.MealRepository;
import org.springframework.transaction.annotation.Transactional;
@SpringBootTest
public class MealServiceTest {
	
	@Autowired
	private MealService mealService;
	
	/** Reference to meal repository */
	@Autowired
	private MealRepository mealRepository;
	

	@BeforeEach
	public void setUp() throws Exception {
		mealRepository.deleteAll();
	}



	@Test
	@Transactional
	public void testCreateMeal() {
		
		MealDto meal1 = new MealDto("Oatmeal", 200, 5, 10, 5, TimeOfDay.BREAKFAST);
				
		MealDto createdMeal1 = mealService.addMeal(meal1);
		assertAll("Meal contents",
				() -> assertEquals("Oatmeal", createdMeal1.getName()),
				() -> assertEquals(200, createdMeal1.getCalories()),
				() -> assertEquals(5, createdMeal1.getCarbohydrates()),
				() -> assertEquals(10, createdMeal1.getProtein()),
				() -> assertEquals(5, createdMeal1.getFat()),
				() -> assertEquals(TimeOfDay.BREAKFAST, createdMeal1.getTimeOfDay()));
		
		MealDto meal2 = new MealDto("Yogurt", 60, 3, 8, 2, TimeOfDay.BREAKFAST);
		MealDto createdMeal2 = mealService.addMeal(meal2);
		assertAll("Meal contents",
				() -> assertEquals("Yogurt", createdMeal2.getName()),
				() -> assertEquals(60, createdMeal2.getCalories()),
				() -> assertEquals(3, createdMeal2.getCarbohydrates()),
				() -> assertEquals(8, createdMeal2.getProtein()),
				() -> assertEquals(2, createdMeal2.getFat()),
				() -> assertEquals(TimeOfDay.BREAKFAST, createdMeal2.getTimeOfDay()));
		
		
	}
	
	@Test
	@Transactional
	public void testGetMealById() {
		
		MealDto meal1 = new MealDto("Oatmeal", 200, 5, 10, 5, TimeOfDay.BREAKFAST);
				
		MealDto createdMeal1 = mealService.addMeal(meal1);
		MealDto fetchedMeal1 = mealService.getMealById(createdMeal1.getId());
		assertAll("Meal contents",
				() -> assertEquals("Oatmeal", fetchedMeal1.getName()),
				() -> assertEquals(200, fetchedMeal1.getCalories()),
				() -> assertEquals(5, fetchedMeal1.getCarbohydrates()),
				() -> assertEquals(10, fetchedMeal1.getProtein()),
				() -> assertEquals(5, fetchedMeal1.getFat()),
				() -> assertEquals(TimeOfDay.BREAKFAST, fetchedMeal1.getTimeOfDay()));
		
	}
	
	@Test
	@Transactional
	public void testUpdateMeal() throws Exception {
		
		MealDto meal1 = new MealDto("Oatmeal", 200, 5, 10, 5, TimeOfDay.BREAKFAST);
		MealDto createdMeal1 = mealService.addMeal(meal1);
		assertAll("Meal contents",
				() -> assertEquals("Oatmeal", createdMeal1.getName()),
				() -> assertEquals(200, createdMeal1.getCalories()),
				() -> assertEquals(5, createdMeal1.getCarbohydrates()),
				() -> assertEquals(10, createdMeal1.getProtein()),
				() -> assertEquals(5, createdMeal1.getFat()),
				() -> assertEquals(TimeOfDay.BREAKFAST, createdMeal1.getTimeOfDay()));
		
		MealDto updatedMeal1 = new MealDto("Oatmeal", 250, 8, 15, 7, TimeOfDay.BREAKFAST);
		MealDto updatedMeal = mealService.updateMeal(createdMeal1.getId(), updatedMeal1);
		assertAll("Meal contents",
				() -> assertEquals("Oatmeal", updatedMeal.getName()),
				() -> assertEquals(250, updatedMeal.getCalories()),
				() -> assertEquals(8, updatedMeal.getCarbohydrates()),
				() -> assertEquals(15, updatedMeal.getProtein()),
				() -> assertEquals(7, updatedMeal.getFat()),
				() -> assertEquals(TimeOfDay.BREAKFAST, updatedMeal.getTimeOfDay()));
		
	}
		
		
	@Test
	@Transactional
	public void testGetMealByName() {
		
		MealDto meal1 = new MealDto("Oatmeal", 200, 5, 10, 5, TimeOfDay.BREAKFAST);
		MealDto createdMeal1 = mealService.addMeal(meal1);
		
		
		
		
		MealDto fetchedMeal1 = mealService.getMealByName(createdMeal1.getName());
		assertAll("Meal contents",
				() -> assertEquals("Oatmeal", fetchedMeal1.getName()),
				() -> assertEquals(200, fetchedMeal1.getCalories()),
				() -> assertEquals(5, fetchedMeal1.getCarbohydrates()),
				() -> assertEquals(10, fetchedMeal1.getProtein()),
				() -> assertEquals(5, fetchedMeal1.getFat()),
				() -> assertEquals(TimeOfDay.BREAKFAST, fetchedMeal1.getTimeOfDay()));
	}
	
	@Test
	@Transactional
	public void testGetAllMeals() {
		
		MealDto meal1 = new MealDto("Oatmeal", 200, 5, 10, 5, TimeOfDay.BREAKFAST);
		mealService.addMeal(meal1);
		
		MealDto meal2 = new MealDto("Yogurt", 60, 3, 8, 2, TimeOfDay.SNACK);
		mealService.addMeal(meal2);
		
		
		List<MealDto> allMeals = mealService.getAllMeals();
		assertAll("Meal 1 contents",
				() -> assertEquals("Oatmeal", allMeals.get(0).getName()),
				() -> assertEquals(200, allMeals.get(0).getCalories()));
		assertAll("Meal 2 contents",
				() -> assertEquals("Yogurt", allMeals.get(1).getName()),
				() -> assertEquals(60, allMeals.get(1).getCalories()));
	}
	
	@Test
	@Transactional
	public void testGetMealByTimeOfDay() {
		MealDto meal1 = new MealDto("Oatmeal", 200, 5, 10, 5, TimeOfDay.BREAKFAST);
		mealService.addMeal(meal1);
		
		List<MealDto> retrievedMeals = mealService.getMealsByTimeOfDay("BreakFast");
		
		assertEquals(retrievedMeals.get(0).getName(), "Oatmeal");
	}

}