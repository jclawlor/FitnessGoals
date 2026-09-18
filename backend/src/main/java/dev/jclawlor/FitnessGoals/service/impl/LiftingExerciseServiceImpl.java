package dev.jclawlor.FitnessGoals.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.jclawlor.FitnessGoals.dto.LiftingExerciseDto;
import dev.jclawlor.FitnessGoals.entity.LiftingExercise;
import dev.jclawlor.FitnessGoals.mapper.LiftingExerciseMapper;
import dev.jclawlor.FitnessGoals.repository.LiftingExerciseRepository;
import dev.jclawlor.FitnessGoals.service.LiftingExerciseService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LiftingExerciseServiceImpl implements LiftingExerciseService {
	
	/** Repository for cardio exercises */
	@Autowired
    private LiftingExerciseRepository liftingRepository;
	
	
	@Autowired
	private LiftingExerciseMapper exerciseMapper;

	@Override
    public LiftingExerciseDto addLiftingExercise (LiftingExerciseDto dto) {
        LiftingExercise exercise = new LiftingExercise();
        exercise.setName(dto.getName());
        exercise.setSets(dto.getSets());
        exercise.setReps(dto.getReps());
        exercise.setWeight(dto.getWeight());
        
        
        LiftingExercise savedLiftingExercise = liftingRepository.save(exercise);
        LiftingExerciseDto savedLiftingExerciseDto = exerciseMapper.mapToLiftingExerciseDto(savedLiftingExercise);
        return savedLiftingExerciseDto;
    }
    
    @Override
    public LiftingExerciseDto updateLiftingExercise(Long id, LiftingExerciseDto dto) {
		LiftingExercise exercise = liftingRepository.findById(id).orElseThrow(() -> new RuntimeException("Lifting exercise not found with id: " + id));
		exercise.setName(dto.getName());
		exercise.setSets(dto.getSets());
		exercise.setReps(dto.getReps());
		exercise.setWeight(dto.getWeight());
		LiftingExercise savedLiftingExercise = liftingRepository.save(exercise);
		return exerciseMapper.mapToLiftingExerciseDto(savedLiftingExercise);

    }
    
    @Override
    public void deleteLiftingExercise (Long id) {
    	if (!liftingRepository.existsById(id)) {
    		throw new RuntimeException("Lifting exercise not found with id: " + id);
    	}
    	liftingRepository.deleteById(id);
    }
    
    @Override
    public List<LiftingExerciseDto> getAllLiftingExercises() {
    	return liftingRepository.findAll().stream()
				.map(exerciseMapper::mapToLiftingExerciseDto)
				.toList();
    }
    
    @Override
    public LiftingExerciseDto getLiftingExerciseById(Long id) {
    	LiftingExercise liftingExercise = liftingRepository.findById(id).orElseThrow(() -> new RuntimeException("Lifting exercise not found with id: " + id));
    	return exerciseMapper.mapToLiftingExerciseDto(liftingExercise);
    }
    
    @Override
    public LiftingExerciseDto getLiftingExerciseByName(String name) {
    	LiftingExercise liftingExercise = liftingRepository.findByName(name).orElseThrow(() -> new RuntimeException("Lifting exercise not found with name: " + name));
		return exerciseMapper.mapToLiftingExerciseDto(liftingExercise);
    }
	

}
