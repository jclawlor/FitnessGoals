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

import dev.jclawlor.FitnessGoals.dto.LiftingExerciseDto;

import dev.jclawlor.FitnessGoals.service.impl.LiftingExerciseServiceImpl;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/lifting")
public class LiftingExerciseController {
	
	@Autowired
	private LiftingExerciseServiceImpl liftingExerciseService;
	
	@PostMapping
    public ResponseEntity<LiftingExerciseDto> addLifting(@Valid @RequestBody LiftingExerciseDto dto) {
        final LiftingExerciseDto savedLiftingExercise = liftingExerciseService.addLiftingExercise(dto);
        return ResponseEntity.ok(savedLiftingExercise);
    }

    @GetMapping
    public ResponseEntity<List<LiftingExerciseDto>> getAllLiftingExercises() {
        return ResponseEntity.ok(liftingExerciseService.getAllLiftingExercises());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<LiftingExerciseDto> getLiftingExerciseById(@PathVariable Long id) {
		LiftingExerciseDto liftingExercise = liftingExerciseService.getLiftingExerciseById(id);
		return ResponseEntity.ok(liftingExercise);
	}
    
    @GetMapping("/name/{name}")
    public ResponseEntity<LiftingExerciseDto> getLiftingExerciseByName(@PathVariable String name) {
    	LiftingExerciseDto liftingExercise = liftingExerciseService.getLiftingExerciseByName(name);
		return ResponseEntity.ok(liftingExercise);
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<LiftingExerciseDto> deleteExercise(@PathVariable Long id) {
		liftingExerciseService.deleteLiftingExercise(id);
		return ResponseEntity.ok().build();
	}
    
    @PutMapping("/{id}")
    public ResponseEntity<LiftingExerciseDto> updateLifting(@PathVariable Long id, @Valid @RequestBody LiftingExerciseDto dto) {
		LiftingExerciseDto updated = liftingExerciseService.updateLiftingExercise(id, dto);
		return ResponseEntity.ok(updated);
	}

    
    
    
	
}
