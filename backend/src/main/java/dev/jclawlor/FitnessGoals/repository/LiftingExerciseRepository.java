package dev.jclawlor.FitnessGoals.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.jclawlor.FitnessGoals.entity.LiftingExercise;

public interface LiftingExerciseRepository extends JpaRepository<LiftingExercise, Long> {
	
	
    Optional<LiftingExercise> findByName(String name);

}
