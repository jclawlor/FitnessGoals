package dev.jclawlor.FitnessGoals.service;

import java.util.List;

import dev.jclawlor.FitnessGoals.dto.LiftingExerciseDto;

public interface LiftingExerciseService {
	
	LiftingExerciseDto addLiftingExercise(LiftingExerciseDto dto);
	
	LiftingExerciseDto getLiftingExerciseById(Long id);
	
	List<LiftingExerciseDto> getAllLiftingExercises();
	
	void deleteLiftingExercise (Long id);
	
	LiftingExerciseDto updateLiftingExercise(Long id, LiftingExerciseDto dto);
	
	LiftingExerciseDto getLiftingExerciseByName(String name);
	
}
