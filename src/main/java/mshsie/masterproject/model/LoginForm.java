package mshsie.masterproject.model;

import javax.validation.constraints.NotBlank; 

public class LoginForm {

	@NotBlank(message="Kjo fushe eshte e detyrueshme")
	private String username;
	
	@NotBlank(message="Kjo fushe eshte e detyrueshme")
	private String password;
	
	public LoginForm() {}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
