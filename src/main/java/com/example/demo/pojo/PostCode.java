package com.example.demo.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "PostCode")
public class PostCode {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "postCodeId")
	@JsonIgnore
	private long postCodeId;
	
	@Column(name ="code")
	@NotBlank(message = "code is mandatory")
	private String code;
	
	@Column(name ="place_name")
	private String placeName;
	
	@Column(name ="longitude")
	private String longitude;
	
	@Column(name ="latitude")
	private String latitude;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="userId", nullable=false)
	@JsonBackReference
	private Customer customer;
	
	
	public PostCode() {}
	
	public PostCode(String code, Customer customer) {
		super();
		this.code = code;
		this.customer = customer;
	}
	
	
	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public Long getPostCodeId() {
		return postCodeId;
	}
	public void setPostCodeId(Long postCodeId) {
		this.postCodeId = postCodeId;
	}
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "PostCode [code=" + code + ", placeName=" + placeName + ", longitude=" + longitude + ", latitude="
				+ latitude + ", customerId=" + customer.getId() + "]";

	}

}
