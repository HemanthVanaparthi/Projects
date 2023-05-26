package com.ms.registration.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserDTO {

	@NotNull(message = "Email can not be null")
	@Pattern(message = "Email is not valid", regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")
	/**
	 * Expected format is abc@abc.com
	 */
	private String email;
	
	@NotNull(message = "Name can not be null")
	private String name;
		
	@NotNull(message = "Phone can not be null")
	@Pattern(message = "Phone number is not valid", regexp = "^(\\([0-9]{3}\\) |[0-9]{3}-)[0-9]{3}-[0-9]{4}$")
	/**
	 * Expected format is (553) 555-1234
	 */
	private String phone;
	
	
	@NotNull(message = "SSN can not be null")
	@Pattern(message = "SSN is not valid", regexp = "^(?!0{3})(?!6{3})[0-8]\\d{2}-(?!0{2})\\d{2}-(?!0{4})\\d{4}$")
	/**
	 * Expected format is 853-45-6789
	 */
	private String ssn;
	
	
	@NotNull(message = "DOB can not be null")
	@Pattern(message = "DOB is not valid", regexp = "^(\\d{4,5}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01]))$")
	/**
	 * Expected format is YYYY-MM-DD
	 */
	private String birthDate;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	
	
}
