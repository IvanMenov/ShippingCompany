package com.example.demo.service;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.exceptions.NoCustomerRegistratedException;
import com.example.demo.pojo.Customer;
import com.example.demo.pojo.PostCode;
import com.example.demo.repository.PostCodeRepository;
import com.example.demo.repository.CustomerRepository;

@Service
public class DBService {
	
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private PostCodeRepository postCodeReposotory;
	
	@Transactional(rollbackFor = Exception.class)
	public void saveCustomer(Customer user) {
		Customer customer = customerRepository.save(user);
		postCodeReposotory.saveAll(customer.getPostCodes());

	}
	
	public List<PostCode> getAllAdressesForCustomerLatestRegistration(String username){
		List<PostCode> list = postCodeReposotory.findPostCodesByUsernamesLastRegistration(username);
		if(list != null && !list.isEmpty()) {
			return list;
		}
		throw new NoCustomerRegistratedException(String.format("No customer with name %s was registered", username));
	}
	public List<Customer> getAllCustomerRegistrationsByName(String name){
		List<Customer> list = customerRepository.findAllByUsername(name);
		if(list != null && !list.isEmpty()) {
			return list;
		}
		throw new NoCustomerRegistratedException(String.format("No customer with name %s was registered", name));
		
	}
	public List<Customer> getAllCustomers(){
		return customerRepository.findAll();
	}
}
