package dev.jclawlor.FitnessGoals.entity;

import dev.jclawlor.FitnessGoals.enums.PaceUnit;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;


@Embeddable
@Data
public class Pace {
	
	private double minutes;
	
	@Enumerated(EnumType.STRING)
	private PaceUnit unit;
	
	public Pace() {
		// Default Constructor
	}
	
	public Pace(double minutes, PaceUnit unit) {
		this.minutes = minutes;
		this.unit = unit;
	}
	
	public void setMinutes(double minutes) {
		this.minutes = minutes;
	}
	
	public double getMinutes() {
		return minutes;
	}
	
	public void setUnit(PaceUnit unit) {
		this.unit = unit;
	}
	
	public PaceUnit getUnit() {
		return unit;
	}

}
