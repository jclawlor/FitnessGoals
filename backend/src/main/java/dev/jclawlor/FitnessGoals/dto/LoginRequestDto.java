package dev.jclawlor.FitnessGoals.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginRequestDto {

    @NotEmpty(message = "Username is required")
    private String username;

    @NotEmpty(message = "Password is required")
    private String password;
    
    public LoginRequestDto(String username, String password) {
		this.username = username;
		this.password = password;
    	
	}
    
    
    public String getUsername() {
    	return username;
    }
    
    public String getPassword() {
		return password;
	}
}