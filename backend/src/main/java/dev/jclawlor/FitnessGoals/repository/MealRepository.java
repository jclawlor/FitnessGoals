package dev.jclawlor.FitnessGoals.repository;

import dev.jclawlor.FitnessGoals.entity.Meal;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MealRepository extends JpaRepository<Meal, Long> {
	
	Optional<Meal> findByName(String name);
	
}
