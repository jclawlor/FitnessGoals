package dev.jclawlor.FitnessGoals.mapper;

import org.springframework.stereotype.Component;

import dev.jclawlor.FitnessGoals.dto.UserDto;
import dev.jclawlor.FitnessGoals.entity.User;

@Component
public class UserMapper {
	
	
	public User mapToUser(final UserDto userDto) {
		User user = new User();
		user.setId(userDto.getId());
		user.setUsername(userDto.getUsername());
		user.setEmail(userDto.getEmail());
		
		return user;
		
	}
	
	public UserDto mapToUserDto(final User user) {
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setUsername(user.getUsername());
		userDto.setEmail(user.getEmail());
		
		return userDto;
	}

}
