package dev.jclawlor.FitnessGoals.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import dev.jclawlor.FitnessGoals.dto.CardioExerciseDto;
import dev.jclawlor.FitnessGoals.dto.PaceDto;
import dev.jclawlor.FitnessGoals.enums.PaceUnit;
import dev.jclawlor.FitnessGoals.repository.CardioExerciseRepository;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class CardioExerciseServiceTest {
	
	/** Reference to lifting exercise repository */
	@Autowired
	private CardioExerciseRepository cardioRepository;
	
	
	@Autowired
	private CardioExerciseService exerciseService;

	@BeforeEach
	public void setUp() throws Exception {
		cardioRepository.deleteAll();
	}
	
	
	@Test
	@Transactional
	public void testAddCardioExercise() {
		PaceDto paceDto = new PaceDto(10.0, PaceUnit.PER_MILE);
		
		CardioExerciseDto ce1 = new CardioExerciseDto("Jog", 20.0, 200, null);
		CardioExerciseDto createdCe1 = exerciseService.addCardioExercise(ce1);
		assertAll("Cardio Exercise contents",
				() -> assertEquals("Jog", createdCe1.getName()),
				() -> assertEquals(20, createdCe1.getDurationMinutes()),
				() -> assertEquals(200, createdCe1.getCaloriesBurned()));
		
		CardioExerciseDto ce2 = new CardioExerciseDto("Row", 30.0, 200, paceDto);
		CardioExerciseDto createdCe2 = exerciseService.addCardioExercise(ce2);
		assertAll("Cardio Exercise contents",
				() -> assertEquals("Row", createdCe2.getName()),
				() -> assertEquals(30.0, createdCe2.getDurationMinutes()),
				() -> assertEquals(200, createdCe2.getCaloriesBurned()),
				() -> assertEquals(paceDto.getMinutes(), createdCe2.getPace().getMinutes()),
				() -> assertEquals(paceDto.getUnit(), createdCe2.getPace().getUnit()));
	}
	
	
	
	
	
	@Test
	@Transactional
	public void testGetCardioExerciseById() {
		PaceDto paceDto = new PaceDto(10.0, PaceUnit.PER_MILE);
		
		CardioExerciseDto ce1 = new CardioExerciseDto("Jog", 20.0, 200, paceDto);
		CardioExerciseDto createdCe1 = exerciseService.addCardioExercise(ce1);
		CardioExerciseDto fetchedCe1 = exerciseService.getCardioExerciseById(createdCe1.getId());
		assertAll("Cardio Exercise contents",
				() -> assertEquals("Jog", fetchedCe1.getName()),
				() -> assertEquals(20, fetchedCe1.getDurationMinutes()),
				() -> assertEquals(200, fetchedCe1.getCaloriesBurned()),
				() -> assertEquals(10, fetchedCe1.getPace().getMinutes()));
		
	}
	
	
	
	@Test
	@Transactional
	public void testUpdateCardioExercise() throws Exception {
		PaceDto paceDto = new PaceDto(10.0, PaceUnit.PER_MILE);
		
		CardioExerciseDto ce1 = new CardioExerciseDto("Jog", 20.0, 200, paceDto);
		CardioExerciseDto createdCe1 = exerciseService.addCardioExercise(ce1);
		assertAll("Cardio Exercise contents",
				() -> assertEquals("Jog", createdCe1.getName()),
				() -> assertEquals(20, createdCe1.getDurationMinutes()),
				() -> assertEquals(200, createdCe1.getCaloriesBurned()),
				() -> assertEquals(10.0, createdCe1.getPace().getMinutes()));
		
		
		CardioExerciseDto updatedCe1 = new CardioExerciseDto("Jog", 20.0, 300, paceDto);
		CardioExerciseDto savedUpdatedCe1 = exerciseService.updateCardioExercise(createdCe1.getId(), updatedCe1);
		assertAll("Cardio Exercise contents",
				() -> assertEquals("Jog", savedUpdatedCe1.getName()),
				() -> assertEquals(20, savedUpdatedCe1.getDurationMinutes()),
				() -> assertEquals(300, savedUpdatedCe1.getCaloriesBurned()),
				() -> assertEquals(10.0, savedUpdatedCe1.getPace().getMinutes()));
		
		PaceDto paceDto2 = new PaceDto(12.0, PaceUnit.PER_MILE);
		CardioExerciseDto updatedCe2 = new CardioExerciseDto("Jog", 20.0, 300, paceDto2);
		CardioExerciseDto savedUpdatedCe2 = exerciseService.updateCardioExercise(createdCe1.getId(), updatedCe2);
		assertAll("Cardio Exercise contents",
				() -> assertEquals("Jog", savedUpdatedCe2.getName()),
				() -> assertEquals(20, savedUpdatedCe2.getDurationMinutes()),
				() -> assertEquals(300, savedUpdatedCe2.getCaloriesBurned()),
				() -> assertEquals(12.0, savedUpdatedCe2.getPace().getMinutes()));
	}
	
	@Test
	@Transactional
	public void testDeleteCardioExercise() throws Exception {
		PaceDto paceDto = new PaceDto(10.0, PaceUnit.PER_MILE);
		
		CardioExerciseDto ce1 = new CardioExerciseDto("Jog", 20.0, 200, paceDto);
		CardioExerciseDto createdCe1 = exerciseService.addCardioExercise(ce1);
		
		exerciseService.deleteCardioExercise(createdCe1.getId());
		
		List<CardioExerciseDto> allCardioExercises = exerciseService.getAllCardioExercises();
		assertTrue(allCardioExercises.isEmpty());
		
	}
		
		
	
	
	@Test
	@Transactional
	public void testGetCardioExerciseByName() throws Exception{
		PaceDto paceDto = new PaceDto(10.0, PaceUnit.PER_MILE);
		
		CardioExerciseDto ce1 = new CardioExerciseDto("Jog", 20.0, 200, paceDto);
		CardioExerciseDto createdCe1 = exerciseService.addCardioExercise(ce1);
		CardioExerciseDto fetchedCe1 = exerciseService.getCardioExerciseByName(createdCe1.getName());
		assertAll("Cardio Exercise contents",
				() -> assertEquals("Jog", fetchedCe1.getName()),
				() -> assertEquals(20, fetchedCe1.getDurationMinutes()),
				() -> assertEquals(200, fetchedCe1.getCaloriesBurned()),
				() -> assertEquals(10, fetchedCe1.getPace().getMinutes()));
		
	}
	
	
	
	@Test
	@Transactional
	public void testGetAllCardioExercises() {
		PaceDto paceDto = new PaceDto(10.0, PaceUnit.PER_MILE);
		
		CardioExerciseDto ce1 = new CardioExerciseDto("Jog", 20.0, 200, paceDto);
		exerciseService.addCardioExercise(ce1);
		
		PaceDto paceDto2 = new PaceDto(2.1, PaceUnit.PER_500M);
		
		CardioExerciseDto ce2 = new CardioExerciseDto("Row", 30, 400, paceDto2);
		exerciseService.addCardioExercise(ce2);
		
		 List<CardioExerciseDto> allCardioExercises = exerciseService.getAllCardioExercises();
		 assertEquals(2, allCardioExercises.size());
		 assertTrue(allCardioExercises.stream().anyMatch(exercise -> exercise.getName().equals("Jog") && 
				 exercise.getDurationMinutes() == 20 && exercise.getCaloriesBurned() == 200 && 
				 exercise.getPace().getMinutes() == paceDto.getMinutes() && 
				 exercise.getPace().getUnit().equals(paceDto.getUnit())));
		 
		 
		 assertTrue(allCardioExercises.stream().anyMatch(exercise -> exercise.getName().equals("Row") && 
				 exercise.getDurationMinutes() == 30 && 
				 exercise.getCaloriesBurned() == 400 && 
				 exercise.getPace().getMinutes() == paceDto2.getMinutes() &&
		 		 exercise.getPace().getUnit().equals(paceDto2.getUnit())));
		 
	}

}
