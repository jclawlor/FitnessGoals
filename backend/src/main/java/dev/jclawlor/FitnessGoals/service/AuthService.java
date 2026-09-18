package dev.jclawlor.FitnessGoals.service;

import dev.jclawlor.FitnessGoals.dto.AuthResponseDto;
import dev.jclawlor.FitnessGoals.dto.LoginRequestDto;
import dev.jclawlor.FitnessGoals.dto.RegisterRequestDto;

public interface AuthService {
    AuthResponseDto register(RegisterRequestDto dto);
    AuthResponseDto login(LoginRequestDto dto);
}