package dev.jclawlor.FitnessGoals.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "lifting_exercises")
@Data
public class LiftingExercise {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	
    private int sets;
    
    private int reps;
    
    private double weight;
    
    public LiftingExercise() {
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