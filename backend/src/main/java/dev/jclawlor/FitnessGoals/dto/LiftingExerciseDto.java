package dev.jclawlor.FitnessGoals.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;



public class LiftingExerciseDto {
	
	private Long id;
	
    @JsonProperty("name")
    @NotBlank
    private String name;
    
    @Positive
    private int sets;
    
    @Positive
    private int reps;
    
    @Positive
    private double weight;
    
    
    public LiftingExerciseDto() {
		// default constructor
	}
    
    public LiftingExerciseDto(String name, int sets, int reps, double weight) {
		this.name = name;
		this.sets = sets;
		this.reps = reps;
		this.weight = weight;
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
    
    public int getSets() {
		return sets;
	}
    
    public void setSets(int sets) {
    	this.sets = sets;
    }
    
    public int getReps() {
    	return reps;
    }
    
    public void setReps(int reps) {
		this.reps = reps;
	}
    
    public double getWeight() {
    	return weight;
    }
    
    public void setWeight(double weight) {
    	this.weight = weight;
    }
    
    
    
    
    
}
