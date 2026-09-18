package dev.jclawlor.FitnessGoals.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.jclawlor.FitnessGoals.dto.UserDto;
import dev.jclawlor.FitnessGoals.entity.User;
import dev.jclawlor.FitnessGoals.exception.UserNotFoundException;
import dev.jclawlor.FitnessGoals.mapper.UserMapper;
import dev.jclawlor.FitnessGoals.repository.UserRepository;
import dev.jclawlor.FitnessGoals.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserMapper userMapper;

	@Override 
	public UserDto getUserByUsername(String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException("Could not find user with this username"));
		return userMapper.mapToUserDto(user);
	}

	@Override
	public void deleteUser(Long id) {
		User existingUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Could not find user with this id"));
		
		userRepository.delete(existingUser);
		
	}

	@Override
	public UserDto getUserById(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("Could not find user with this id"));
		return userMapper.mapToUserDto(user);
	}

}
