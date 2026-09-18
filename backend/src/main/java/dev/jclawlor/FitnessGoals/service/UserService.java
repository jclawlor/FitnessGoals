package dev.jclawlor.FitnessGoals.service;


import dev.jclawlor.FitnessGoals.dto.UserDto;


public interface UserService {
	
	UserDto getUserByUsername(String username);
        
    void deleteUser(Long id);
    
    UserDto getUserById(Long id);
    
    

}
