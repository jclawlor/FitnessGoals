package dev.jclawlor.FitnessGoals.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequestDto {

    @NotEmpty(message = "Username is required")
    private String username;

    @NotEmpty(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotEmpty(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
    
    public RegisterRequestDto(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}
    	
		
    
    public String getUsername() {
		return username;
	}
    
    public String getEmail() {
    	return email;
    }
    
    public String getPassword() {
		return password;
	}
    
}