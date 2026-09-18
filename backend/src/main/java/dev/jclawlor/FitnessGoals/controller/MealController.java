package dev.jclawlor.FitnessGoals.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dev.jclawlor.FitnessGoals.dto.MealDto;
import dev.jclawlor.FitnessGoals.service.MealService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/meals")
public class MealController {

    @Autowired
    private MealService mealService;
    

    @GetMapping
    public List<MealDto> getMeals() {
        return mealService.getAllMeals();
    }
    
    @GetMapping("{id}")
    public ResponseEntity<MealDto> getMealById(@PathVariable("id") final Long id) {
		final MealDto mealDto = mealService.getMealById(id);
		return ResponseEntity.ok(mealDto);
	}
    
    @GetMapping("/timeOfDay/{timeOfDay}")
    public ResponseEntity<List<MealDto>> getMealsByTimeOfDay(@PathVariable("timeOfDay") final String timeOfDay) {
    	final List<MealDto> mealDtos = mealService.getMealsByTimeOfDay(timeOfDay);
    	return ResponseEntity.ok(mealDtos);
    	
    }

    @PostMapping
    public ResponseEntity<MealDto> addMeal(@Valid @RequestBody final MealDto mealDto) {
        final MealDto savedMealDto = mealService.addMeal(mealDto);
        return ResponseEntity.ok(savedMealDto);
    }

    @PutMapping("{id}")
    public ResponseEntity<MealDto> updateMeal(@PathVariable("id") final Long id,
                                              @Valid @RequestBody final MealDto mealDto) {
    	
        final MealDto savedMealDto = mealService.updateMeal(id, mealDto);
        return ResponseEntity.ok(savedMealDto);
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteMeal(@PathVariable("id") final Long id) {
		mealService.deleteMeal(id);
		return ResponseEntity.ok( "Meal deleted successfully" );
	}
    
    
}
