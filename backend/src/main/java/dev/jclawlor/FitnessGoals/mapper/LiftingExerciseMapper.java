package dev.jclawlor.FitnessGoals.mapper;

import org.springframework.stereotype.Component;

import dev.jclawlor.FitnessGoals.dto.LiftingExerciseDto;
import dev.jclawlor.FitnessGoals.entity.LiftingExercise;

@Component
public class LiftingExerciseMapper {
	
	
	/**
	 * Converts a LiftingExerciseDto to a LiftingExercise entity.
	 * 
	 * 
	 * @param liftingExerciseDto
	 * @return
	 */
	public LiftingExercise mapToLiftingExercise(final LiftingExerciseDto liftingExerciseDto) {
		LiftingExercise liftingExercise = new LiftingExercise();
		liftingExercise.setId(liftingExerciseDto.getId());
		liftingExercise.setName(liftingExerciseDto.getName());
		liftingExercise.setSets(liftingExerciseDto.getSets());
		liftingExercise.setReps(liftingExerciseDto.getReps());
		liftingExercise.setWeight(liftingExerciseDto.getWeight());
		
		return liftingExercise;
		
	}
	
	
	
	/**
	 * Converts a LiftingExercise entity to a LiftingExerciseDto.
	 * 
	 * 
	 * @param LiftingExercise
	 * @return
	 */
	public LiftingExerciseDto mapToLiftingExerciseDto(final LiftingExercise LiftingExercise) {
		LiftingExerciseDto liftingExerciseDto = new LiftingExerciseDto();
		liftingExerciseDto.setId(LiftingExercise.getId());
		liftingExerciseDto.setName(LiftingExercise.getName());
		liftingExerciseDto.setSets(LiftingExercise.getSets());
		liftingExerciseDto.setReps(LiftingExercise.getReps());
		liftingExerciseDto.setWeight(LiftingExercise.getWeight());
		
		return liftingExerciseDto;
	}
	
	

}
