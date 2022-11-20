package com.example.demo.pojo;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "Customer")
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "userId")
	@JsonIgnore
	private long userId;
	
	@Column(name = "username")
	@NotBlank(message = "username is mandatory")
	private String username;
	
	@OneToMany(mappedBy = "customer" ,fetch = FetchType.LAZY)
	@JsonManagedReference
	@NotEmpty(message = "postCodes are mandatory")
	private List<PostCode> postCodes;
	
	@Column(name = "registrationTime")
	private LocalDateTime registrationTime = LocalDateTime.now();
	
	public Customer() {}
	
	public Customer(String username, List<PostCode> postCodes) {
		super();
		this.username = username;
		this.postCodes = postCodes;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<PostCode> getPostCodes() {
		return postCodes;
	}
	public void setPostCodes(List<PostCode> postCodes) {
		postCodes.forEach(code -> code.setCustomer(this));
		this.postCodes = postCodes;
	}
	public long getId() {
		return userId;
	}
	public void setId(long id) {
		this.userId = id;
	}

	public LocalDateTime getRegistrationTime() {
		return registrationTime;
	}

	@Override
	public String toString() {
		return "Customer [username=" + username + ", registrationTime=" + registrationTime + "]";
	}
	
}
