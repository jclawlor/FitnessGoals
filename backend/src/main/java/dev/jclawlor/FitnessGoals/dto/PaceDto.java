package dev.jclawlor.FitnessGoals.dto;

import dev.jclawlor.FitnessGoals.enums.PaceUnit;
import lombok.Data;

public class PaceDto {
	private double minutes;
	private PaceUnit unit;
	
	public PaceDto() {
		// Default Constructor
	}
	
	public PaceDto(double minutes, PaceUnit unit) {
		this.minutes = minutes;
		this.unit = unit;
	}
	
	public double getMinutes() {
		return minutes;
	}
	
	public PaceUnit getUnit() {
		return unit;
	}
}
