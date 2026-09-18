package dev.jclawlor.FitnessGoals.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.jclawlor.FitnessGoals.dto.MealDto;
import dev.jclawlor.FitnessGoals.entity.Meal;
import dev.jclawlor.FitnessGoals.enums.TimeOfDay;
import dev.jclawlor.FitnessGoals.exception.ResourceNotFoundException;
import dev.jclawlor.FitnessGoals.mapper.MealMapper;
import dev.jclawlor.FitnessGoals.mapper.TimeOfDayMapper;
import dev.jclawlor.FitnessGoals.repository.MealRepository;
import dev.jclawlor.FitnessGoals.service.MealService;

@Service
public class MealServiceImpl implements MealService {
	
	
	@Autowired
	private MealRepository mealRepository;
	
	@Autowired
	private MealMapper mealMapper;
	
	@Autowired
	private TimeOfDayMapper timeOfDayMapper;
	
	
	@Override
	public List<MealDto> getAllMeals() {
		final List<Meal> meals = mealRepository.findAll();
		return meals.stream().map( ( meal ) -> mealMapper.mapToMealDto( meal ) )
                .collect( Collectors.toList() );
		
		
	}
	
	@Override
	public MealDto getMealById(Long id) {
		Meal meal = mealRepository.findById(id).orElseThrow(() -> new RuntimeException("Meal not found with id: " + id));
		return mealMapper.mapToMealDto(meal);
	}
	
	@Override
	public List<MealDto> getMealsByTimeOfDay(String timeOfDay) {
		final List<Meal> meals = mealRepository.findAll();
		TimeOfDay timeOfDayEnum = timeOfDayMapper.mapToTimeOfDayToEnum(timeOfDay);
		List <MealDto> mealsByTimeOfDay = new ArrayList<MealDto>();
		for (Meal meal : meals) {
			if (meal.getTimeOfDay().equals(timeOfDayEnum)) {
				mealsByTimeOfDay.add(mealMapper.mapToMealDto(meal));
			}
		}
		
		return mealsByTimeOfDay;
		
	}
	

    @Override
    public MealDto addMeal(MealDto mealDto) {
        // TODO: persist the meal using your repository
        // For now return the same DTO (or populate an id)
    	
    	Meal meal = mealMapper.mapToMeal(mealDto);
    	Meal savedMeal = mealRepository.save(meal);
    	MealDto savedMealDto = mealMapper.mapToMealDto(savedMeal);
    	
    	
    	
        return savedMealDto;
    }

    @Override
    public MealDto updateMeal(Long id, MealDto mealDto) {
        // TODO: update the meal with given id using your repository
        // For now return the DTO (you might set the id on the DTO)
    	
    	Meal existingMeal = mealRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Meal not found with id: " + id));
    	existingMeal.setName(mealDto.getName());
    	existingMeal.setCalories(mealDto.getCalories());
    	existingMeal.setCarbohydrates(mealDto.getCarbohydrates());
    	existingMeal.setProtein(mealDto.getProtein());
    	existingMeal.setFat(mealDto.getFat());
    	
    	Meal updatedMeal = mealRepository.save(existingMeal);
    	
    	MealDto updatedMealDto = mealMapper.mapToMealDto(updatedMeal);
    	
    	
        return updatedMealDto;
    }
    
    @Override
    public void deleteMeal(Long id) {
    	Meal meal = mealRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Meal not found with id: " + id));    	
    	
    	mealRepository.delete(meal);
    	
    }
    
    @Override
    public MealDto getMealByName(String name) {
		Meal meal = mealRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Meal not found with name: " + name));
		return mealMapper.mapToMealDto(meal);
	}
    
}

