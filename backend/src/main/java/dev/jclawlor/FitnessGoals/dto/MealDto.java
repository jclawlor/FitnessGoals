package dev.jclawlor.FitnessGoals.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import dev.jclawlor.FitnessGoals.enums.TimeOfDay;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public class MealDto {
	
	private Long id;
	
	@JsonProperty("name")
	@NotBlank
	private String name;
	
	@Positive
	private Integer calories;
	
	@Positive
	private Integer carbohydrates;
	
	@Positive
	private Integer protein;
	
	@Positive
	private Integer fat;
	
	@NotNull(message = "Time of day is required")
	private TimeOfDay timeOfDay; // e.g., "Breakfast", "Lunch", "Dinner", "Snack"
	
	public MealDto (){
		// default constructor
		
	}

	public MealDto(String string, int calories, int carbohydrates, int protein, int fat, TimeOfDay timeOfDay) {
		this.name = string;
		this.calories = calories;
		this.carbohydrates = carbohydrates;
		this.protein = protein;
		this.fat = fat;
		this.timeOfDay = timeOfDay;
	}

	public void setId(Long id2) {
		this.id = id2;
	}
	
	public String getName() {
		return name;
	}
	
	public Integer getCalories() {
		return calories;
	}
	
	public Integer getCarbohydrates() {
		return carbohydrates;
	}
	
	public Integer getProtein() {
		return protein;
	}
	
	public Integer getFat() {
		return fat;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setCalories(Integer calories) {
		this.calories = calories;
	}
	
	public void setCarbohydrates(Integer carbohydrates) {
		this.carbohydrates = carbohydrates;
	}
	
	public void setProtein(Integer protein) {
		this.protein = protein;
	}
	
	public void setFats(Integer fat) {
		this.fat = fat;
	}
	
	public TimeOfDay getTimeOfDay() {
		return timeOfDay;
	}
	
	public void setTimeOfDay(TimeOfDay timeOfDay) {
		this.timeOfDay = timeOfDay;
	}

	

}
