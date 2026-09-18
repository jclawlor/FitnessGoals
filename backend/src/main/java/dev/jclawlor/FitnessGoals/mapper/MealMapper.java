package dev.jclawlor.FitnessGoals.mapper;


import org.springframework.stereotype.Component;

import dev.jclawlor.FitnessGoals.dto.MealDto;
import dev.jclawlor.FitnessGoals.entity.Meal;

@Component
public class MealMapper {
	
	
	
	public Meal mapToMeal(final MealDto mealDto) {
		Meal meal = new Meal();
		meal.setId(mealDto.getId());
		meal.setName(mealDto.getName());
		meal.setCalories(mealDto.getCalories());
		meal.setProtein(mealDto.getProtein());
		meal.setCarbohydrates(mealDto.getCarbohydrates());
		meal.setFat(mealDto.getFat());
		meal.setTimeOfDay(mealDto.getTimeOfDay());
		
		return meal;
		
	}
	
	
	
	/**
	 * Converts a Meal entity to a MealDto.
	 * 
	 * 
	 * @param meal
	 * @return
	 */
	public MealDto mapToMealDto(final Meal meal) {
		MealDto mealDto = new MealDto();
		mealDto.setId(meal.getId());
		mealDto.setName(meal.getName());
		mealDto.setCalories(meal.getCalories());
		mealDto.setProtein(meal.getProtein());
		mealDto.setCarbohydrates(meal.getCarbohydrates());
		mealDto.setFats(meal.getFat());
		mealDto.setTimeOfDay(meal.getTimeOfDay());
		
		
		return mealDto;
	}

}
