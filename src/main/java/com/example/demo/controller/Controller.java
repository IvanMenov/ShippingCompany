package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.pojo.Customer;
import com.example.demo.pojo.DeserializedPostCode;
import com.example.demo.pojo.PostCode;
import com.example.demo.service.DBService;
import com.example.demo.utilities.Utils;

@RestController
public class Controller {
	@Autowired
	private DBService service;
	
	@PostMapping("/api/v1/register")
	public ResponseEntity<String> registerCustomer(@RequestBody Customer customer) {
		customer.getPostCodes().forEach(code -> {
			DeserializedPostCode deserialized = Utils.deserialize(code.getCode(), DeserializedPostCode.class);
			deserialized.getPlaces().forEach(place -> {
				code.setPlaceName(place.getPlaceName());
				code.setLatitude(place.getLatitude());
				code.setLongitude(place.getLongitude());
			});
		});
		service.saveCustomer(customer);
		return new ResponseEntity<String>("Successfully created", HttpStatus.CREATED);

	}
	@GetMapping("/api/v1/customers")
	public ResponseEntity<List<Customer>> getRegisteredCustomers(){
		return new ResponseEntity<List<Customer>>(service.getAllCustomers(), HttpStatus.OK);
	}
	
	@GetMapping("/api/v1/customers/{name}")
	public ResponseEntity<List<Customer>> getAllRegistrationsByUserName(@PathVariable String name){
		List<Customer> customers = service.getAllCustomerRegistrationsByName(name);
		return new ResponseEntity<List<Customer>>(customers, HttpStatus.OK);
	}

	
	@GetMapping("/api/v1/customers/{customerName}/addresses/latestregistration")
	public ResponseEntity<List<PostCode>> getUserAdressesFromLatestRegistrationTime(@PathVariable String customerName){
		return new ResponseEntity<List<PostCode>>(service.getAllAdressesForCustomerLatestRegistration(customerName), HttpStatus.OK);
	}
}
