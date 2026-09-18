package dev.jclawlor.FitnessGoals.mapper;

import org.springframework.stereotype.Component;

import dev.jclawlor.FitnessGoals.dto.CardioExerciseDto;
import dev.jclawlor.FitnessGoals.dto.PaceDto;
import dev.jclawlor.FitnessGoals.entity.CardioExercise;
import dev.jclawlor.FitnessGoals.entity.Pace;


@Component
public class CardioExerciseMapper {
	
	/**
	 * Converts a CardioExerciseDto to a CardioExercise entity.
	 * 
	 * 
	 * @param dto
	 * @return
	 */
	public CardioExercise mapToCardioExercise(final CardioExerciseDto dto) {
		CardioExercise cardioExercise = new CardioExercise();
		cardioExercise.setId(dto.getId());
		cardioExercise.setName(dto.getName());
		cardioExercise.setDurationMinutes(dto.getDurationMinutes());
		cardioExercise.setCaloriesBurned(dto.getCaloriesBurned());
		
		
		if (dto.getPace() != null) {
			cardioExercise.setPace(new Pace( 
					dto.getPace().getMinutes(), 
					dto.getPace().getUnit()));
		}
		
		
		return cardioExercise;
		
	}
	
	
	
	/**
	 * Converts a CardioExercise entity to a CardioExerciseDto.
	 * 
	 * 
	 * @param exercise
	 * @return
	 */
	public CardioExerciseDto mapToCardioExerciseDto (final CardioExercise exercise) {
		CardioExerciseDto cardioExerciseDto = new CardioExerciseDto();
		cardioExerciseDto.setId(exercise.getId());
		cardioExerciseDto.setName(exercise.getName());
		cardioExerciseDto.setDurationMinutes(exercise.getDurationMinutes());
		cardioExerciseDto.setCaloriesBurned(exercise.getCaloriesBurned());


		if (exercise.getPace() != null) {
			cardioExerciseDto.setPace(new PaceDto(
				exercise.getPace().getMinutes(),
				exercise.getPace().getUnit()
			));
		}
		
		return cardioExerciseDto;
	}

}
