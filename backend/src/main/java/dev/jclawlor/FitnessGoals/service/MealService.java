package dev.jclawlor.FitnessGoals.service;

import java.util.List;

import dev.jclawlor.FitnessGoals.dto.MealDto;

public interface MealService {
    MealDto addMeal(MealDto mealDto);
    MealDto updateMeal(Long id, MealDto mealDto);
    List<MealDto> getAllMeals();
    void deleteMeal(Long id);
    MealDto getMealById(Long id);
    List<MealDto> getMealsByTimeOfDay(String timeOfDay);
    MealDto getMealByName(String name);
}
