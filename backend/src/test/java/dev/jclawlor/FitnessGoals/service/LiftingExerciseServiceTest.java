package dev.jclawlor.FitnessGoals.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import dev.jclawlor.FitnessGoals.dto.LiftingExerciseDto;
import dev.jclawlor.FitnessGoals.repository.LiftingExerciseRepository;
import org.springframework.transaction.annotation.Transactional;
@SpringBootTest
public class LiftingExerciseServiceTest {
	
	
	/** Reference to lifting exercise repository */
	@Autowired
	private LiftingExerciseRepository liftingRepository;
	
	
	@Autowired
	private LiftingExerciseService exerciseService;

	@BeforeEach
	public void setUp() throws Exception {
		liftingRepository.deleteAll();
	}



	@Test
	@Transactional
	public void testAddLiftingExercise() {
		
		LiftingExerciseDto le1 = new LiftingExerciseDto("Squat", 3, 6, 225);
		LiftingExerciseDto createdLe1 = exerciseService.addLiftingExercise(le1);
		assertAll("Lifting Exercise contents",
				() -> assertEquals("Squat", createdLe1.getName()),
				() -> assertEquals(3, createdLe1.getSets()),
				() -> assertEquals(6, createdLe1.getReps()),
				() -> assertEquals(225, createdLe1.getWeight()));
		
		
		
		
	}
	
	
	
	
	@Test
	@Transactional
	public void testGetLiftingExerciseById() {
		
		LiftingExerciseDto le1 = new LiftingExerciseDto("Squat", 3, 6, 225);
		LiftingExerciseDto createdLe1 = exerciseService.addLiftingExercise(le1);
		LiftingExerciseDto fetchedExercise = exerciseService.getLiftingExerciseById(createdLe1.getId());
		assertAll("Lifting Exercise contents",
				() -> assertEquals("Squat", fetchedExercise.getName()),
				() -> assertEquals(3, fetchedExercise.getSets()),
				() -> assertEquals(6, fetchedExercise.getReps()),
				() -> assertEquals(225, fetchedExercise.getWeight()));
		
	}

	
	@Test
	@Transactional
	public void testUpdateLiftingExercise() throws Exception {
		
		LiftingExerciseDto le1 = new LiftingExerciseDto("Squat", 3, 6, 225);
		LiftingExerciseDto createdLe1 = exerciseService.addLiftingExercise(le1);
		assertAll("Lifting Exercise contents",
				() -> assertEquals("Squat", createdLe1.getName()),
				() -> assertEquals(3, createdLe1.getSets()),
				() -> assertEquals(6, createdLe1.getReps()),
				() -> assertEquals(225, createdLe1.getWeight()));
		
		
		LiftingExerciseDto updatedLe1 = new LiftingExerciseDto("Squat", 3, 6, 315);
		LiftingExerciseDto savedUpdatedLe1 = exerciseService.updateLiftingExercise(createdLe1.getId(), updatedLe1);
		assertAll("Lifting Exercise contents",
				() -> assertEquals("Squat", savedUpdatedLe1.getName()),
				() -> assertEquals(3, savedUpdatedLe1.getSets()),
				() -> assertEquals(6, savedUpdatedLe1.getReps()),
				() -> assertEquals(315, savedUpdatedLe1.getWeight()));
		
		
	}
	
	
		
		
	@Test
	@Transactional
	public void testGetLiftingExerciseByName() throws Exception {
		
		LiftingExerciseDto le1 = new LiftingExerciseDto("Squat", 3, 6, 225);
		LiftingExerciseDto createdLe1 = exerciseService.addLiftingExercise(le1);
		
		LiftingExerciseDto fetchedLe1 = exerciseService.getLiftingExerciseByName(createdLe1.getName());
		assertAll("Lifting Exercise contents",
				() -> assertEquals("Squat", fetchedLe1.getName()),
				() -> assertEquals(3, fetchedLe1.getSets()),
				() -> assertEquals(6, fetchedLe1.getReps()),
				() -> assertEquals(225, fetchedLe1.getWeight()));
		
	}
	
	
	
	@Test
	@Transactional
	public void testGetAllLiftingExercises() {
		LiftingExerciseDto le1 = new LiftingExerciseDto("Squat", 3, 6, 225);
		LiftingExerciseDto createdLe1 = exerciseService.addLiftingExercise(le1);
		assertAll("Lifting Exercise contents",
				() -> assertEquals("Squat", createdLe1.getName()),
				() -> assertEquals(3, createdLe1.getSets()),
				() -> assertEquals(6, createdLe1.getReps()),
				() -> assertEquals(225, createdLe1.getWeight()));
		
		LiftingExerciseDto le2 = new LiftingExerciseDto("Bench Press", 3, 10, 185);
		LiftingExerciseDto createdLe2 = exerciseService.addLiftingExercise(le2);
		assertAll("Lifting Exercise contents",
				() -> assertEquals("Bench Press", createdLe2.getName()),
				() -> assertEquals(3, createdLe2.getSets()),
				() -> assertEquals(10, createdLe2.getReps()),
				() -> assertEquals(185, createdLe2.getWeight()));
		
		
		
		 List<LiftingExerciseDto> allLiftingExercises = exerciseService.getAllLiftingExercises();
		 assertEquals(2, allLiftingExercises.size());
		 assertTrue(allLiftingExercises.stream().anyMatch(exercise -> exercise.getName().equals("Squat") && exercise.getSets() == 3 && exercise.getReps() == 6 && exercise.getWeight() == 225));
		 assertTrue(allLiftingExercises.stream().anyMatch(exercise -> exercise.getName().equals("Bench Press") && exercise.getSets() == 3 && exercise.getReps() == 10 && exercise.getWeight() == 185));
		 
		 
		 
		 
	}
	

}