package dev.jclawlor.FitnessGoals.entity;

import dev.jclawlor.FitnessGoals.enums.TimeOfDay;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "meals")
@Data
public class Meal {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private int calories;
	
	private int carbohydrates;
	
	private int protein;
	
	private int fat;
	
	@Enumerated(EnumType.STRING)
	private TimeOfDay timeOfDay; // e.g., "Breakfast", "Lunch", "Dinner", "Snack"
	
	public Meal() {
		// default constructor
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getCalories() {
		return calories;
	}
	
	public void setCalories(int calories) {
		this.calories = calories;
	}
	
	public int getCarbohydrates() {
		return carbohydrates;
	}
	
	public void setCarbohydrates(int carbohydrates) {
		this.carbohydrates = carbohydrates;
	}
	
	public int getProtein() {
		return protein;
	}
	
	public void setProtein(int protein) {
		this.protein = protein;
	}
	
	public int getFat() {
		return fat;
	}
	
	public void setFat(int fat) {
		this.fat = fat;
	}
	
	public TimeOfDay getTimeOfDay() {
		return timeOfDay;
	}
	
	public void setTimeOfDay(TimeOfDay timeOfDay) {
		this.timeOfDay = timeOfDay;
	}
	
	
	
	
}
