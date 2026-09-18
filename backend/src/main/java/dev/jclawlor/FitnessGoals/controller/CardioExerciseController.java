package dev.jclawlor.FitnessGoals.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.jclawlor.FitnessGoals.dto.CardioExerciseDto;
import dev.jclawlor.FitnessGoals.entity.CardioExercise;
import dev.jclawlor.FitnessGoals.service.impl.CardioExerciseServiceImpl;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cardio")
public class CardioExerciseController {
	
	@Autowired
	private CardioExerciseServiceImpl exerciseService;


    @PostMapping
    public ResponseEntity<CardioExerciseDto> addCardio(@Valid @RequestBody CardioExerciseDto dto) {
        final CardioExerciseDto savedCardioExercise = exerciseService.addCardioExercise(dto);
        return ResponseEntity.ok(savedCardioExercise);
    }
    
    @GetMapping
    public ResponseEntity<List<CardioExerciseDto>> getAllCardioExercises() {
		return ResponseEntity.ok(exerciseService.getAllCardioExercises());
	}
    
    @GetMapping("/{id}")
    public ResponseEntity<CardioExerciseDto> getCardioExerciseById(@PathVariable Long id) {
    	CardioExerciseDto cardioExercise = exerciseService.getCardioExerciseById(id);
		return ResponseEntity.ok(cardioExercise);
    }
    
    @GetMapping("/name/{name}")
    public ResponseEntity<CardioExerciseDto> getCardioExerciseByName(@PathVariable String name) {
		CardioExerciseDto cardioExercise = exerciseService.getCardioExerciseByName(name);
		return ResponseEntity.ok(cardioExercise);
		
    }
    
    
    @DeleteMapping("/{id}")
    public ResponseEntity<CardioExercise> deleteCardioExercise(@PathVariable Long id) {
		exerciseService.deleteCardioExercise(id);
		return ResponseEntity.ok().build();
	}
    
    
    @PutMapping("/{id}")
    public ResponseEntity<CardioExerciseDto> updateCardioExercise(@PathVariable Long id, @Valid @RequestBody CardioExerciseDto dto) {
    	CardioExerciseDto updated = exerciseService.updateCardioExercise(id, dto);
    	return ResponseEntity.ok(updated);
    }

}
