package dev.jclawlor.FitnessGoals.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.jclawlor.FitnessGoals.entity.DailyLog;


public interface DailyLogRepository extends JpaRepository<DailyLog, Long> {
	
    Optional<DailyLog> findByDate(LocalDate date);
    
    List<DailyLog> findByUserUsername(String username);
    
    DailyLog findByUserUsernameAndDate(String username, LocalDate date);

}
