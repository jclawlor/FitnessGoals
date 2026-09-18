package dev.jclawlor.FitnessGoals.dto;


public class UserDto {
	
	private Long id;
	
    private String username;

    private String email;


	
	
	public UserDto() {
		// default constructor
	}
	
	
	public UserDto(String username, String email, String password) {
		this.username = username;
		this.email = email;
//		this.password = password;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	

}
