package dev.jclawlor.FitnessGoals.service;

import java.util.List;

import dev.jclawlor.FitnessGoals.dto.CardioExerciseDto;

public interface CardioExerciseService {
	
	CardioExerciseDto addCardioExercise(CardioExerciseDto dto);
	
	CardioExerciseDto getCardioExerciseById(Long id);
	
	CardioExerciseDto getCardioExerciseByName(String name);
	
	CardioExerciseDto updateCardioExercise(Long id, CardioExerciseDto dto);
	
	void deleteCardioExercise(Long id);
	
	List<CardioExerciseDto> getAllCardioExercises();
	


}
