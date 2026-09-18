package dev.jclawlor.FitnessGoals.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.jclawlor.FitnessGoals.dto.DailyLogDto;
import dev.jclawlor.FitnessGoals.entity.DailyLog;
import dev.jclawlor.FitnessGoals.entity.User;
import dev.jclawlor.FitnessGoals.repository.UserRepository;

@Component
public class DailyLogMapper {
	
	@Autowired
	private UserRepository userRepository;
	
	
	
	public DailyLog mapToDailyLog(final DailyLogDto dailyLogDto) {
		User user = userRepository.findByUsername(dailyLogDto.getUsername())
				.orElseThrow(() -> new RuntimeException("User not found with username: " + dailyLogDto.getUsername()));
		
		DailyLog log = new DailyLog();
		log.setId(dailyLogDto.getId());
		log.setUser(user);
		log.setDate(dailyLogDto.getDate());
		log.setMeals(dailyLogDto.getMeals());
		log.setLiftingExercises(dailyLogDto.getLiftingExercises());
		log.setCardioExercises(dailyLogDto.getCardioExercises());
		log.setWaterOunces(dailyLogDto.getWaterOunces());
		return log;
		
	}
	
	public DailyLogDto mapToDailyLogDto(final DailyLog dailyLog) {
		DailyLogDto logDto = new DailyLogDto();
		logDto.setId(dailyLog.getId());
		logDto.setUsername(dailyLog.getUser().getUsername());
		logDto.setDate(dailyLog.getDate());
		logDto.setMeals(dailyLog.getMeals());
		logDto.setLiftingExercises(dailyLog.getLiftingExercises());
		logDto.setCardioExercises(dailyLog.getCardioExercises());
		logDto.setWaterOunces(dailyLog.getWaterOunces());
		return logDto;
		
	}

}
