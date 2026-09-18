package dev.jclawlor.FitnessGoals.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.jclawlor.FitnessGoals.entity.CardioExercise;

public interface CardioExerciseRepository extends JpaRepository<CardioExercise, Long> {
	
	
	Optional<CardioExercise> findByName(String name);

}
