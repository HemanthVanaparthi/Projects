package com.ms.registration.dao.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
@Table(name="user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
    @Column(columnDefinition = "varchar(255)", nullable = false)
	private String name;
	
	@Column(columnDefinition = "varchar(64)", nullable = false)
	private String SSN;
	
	@Column(columnDefinition = "varchar(255)", nullable = false)
	private String email;
	
	@Column(columnDefinition = "varchar(64)", nullable = false)
	private String phone;
	
    @Column(columnDefinition = "varchar(128)", nullable = false)
	private String birthDate; 
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdOn")
	private LocalDateTime createdOn; // User create date

	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modifiedOn")
	private LocalDateTime modifiedOn; // User modified date

	@Column(columnDefinition = "varchar(30)", nullable = false)
	private String status;
	
	@PrePersist
    public void prePersist() {
        createdOn =  LocalDateTime.now();
    }
 
    @PreUpdate
    public void preUpdate() {
    	modifiedOn = LocalDateTime.now();
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSSN() {
		return SSN;
	}

	public void setSSN(String sSN) {
		SSN = sSN;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public LocalDateTime getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(LocalDateTime modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	
}
