package dev.jclawlor.FitnessGoals.mapper;

import org.springframework.stereotype.Component;

import dev.jclawlor.FitnessGoals.enums.TimeOfDay;

@Component
public class TimeOfDayMapper {
	
	public TimeOfDay mapToTimeOfDayToEnum(String timeOfDay) {
		if (timeOfDay == null || timeOfDay.isEmpty()) {
			throw new IllegalArgumentException("timeOfDay cannot be null or empty");
		}

		try {
			return TimeOfDay.valueOf(timeOfDay.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid timeOfDay: " + timeOfDay, e);
		}

	}
	

}
