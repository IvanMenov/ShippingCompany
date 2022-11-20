package com.example.demo;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.pojo.Customer;
import com.example.demo.pojo.PostCode;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static com.example.demo.Constants.ADDRESS;
import static com.example.demo.Constants.ENDPOINT_GET_REGISTERED_USER_BY_NAME;
import static com.example.demo.Constants.ENDPOINT_LATEST_REGISTERED_USER_BY_NAME;
import static com.example.demo.Constants.ENDPOINT_GET_REGISTERED_USERS;
import static com.example.demo.Constants.ENDPOINT_REGISTER_CUSTOMER;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ShippingCompanyApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int randomServerPort;
	
	@Test
	@Order(1)
	public void testRegistrationWithoutUsername() throws URISyntaxException {
		Customer customer = new Customer();
		List<PostCode> listPostCodes = new ArrayList<>();
		for (int i = 0; i < 1; i++) {
			PostCode postCode = new PostCode("11221", customer);
			listPostCodes.add(postCode);
		}
		customer.setPostCodes(listPostCodes);

		ResponseEntity<String> result = makePostRequest(customer, String.class);
		assertTrue(result.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR));
		assertTrue(result.getBody().equals("Could not save the customer/post codes!"));

	}

	@Test
	@Order(2)
	public void testRegistrationWithIncorrectPostCode() throws URISyntaxException {	
		Customer customer = new Customer();
		customer.setUsername("Ivan");
		List<PostCode> listPostCodes = new ArrayList<>();
		for (int i = 0; i < 1; i++) {
			PostCode postCode = new PostCode("123456789", customer);
			listPostCodes.add(postCode);
		}
		customer.setPostCodes(listPostCodes);
		ResponseEntity<String> result = makePostRequest(customer, String.class);
		assertTrue(result.getStatusCode().equals(HttpStatus.BAD_REQUEST));
		assertTrue(result.getBody().equals("Unable to connect to https://api.zippopotam.us/us/123456789"));
	}
	
	@Test
	@Order(3)
	public void testSuccessFulRegistration() throws URISyntaxException {	
		Customer customer = new Customer();
		customer.setUsername("Ivan");
		List<PostCode> listPostCodes = new ArrayList<>();

		PostCode postCode = new PostCode("30040", customer);
		listPostCodes.add(postCode);
		customer.setPostCodes(listPostCodes);
		
		ResponseEntity<String> result = makePostRequest(customer, String.class);
		assertTrue(result.getStatusCode().equals(HttpStatus.CREATED));
		assertTrue(result.getBody().equals("Successfully created"));
	}
	
	@Test
	@Order(4)
	public void testGetAllRegistrations() throws URISyntaxException {	
		Customer customer = new Customer();
		customer.setUsername("Ivan");
		List<PostCode> listPostCodes = new ArrayList<>();
		for (int i = 0; i < 1; i++) {
			PostCode postCode = new PostCode("30040", customer);
			listPostCodes.add(postCode);
		}
		URI uri = new URI(ADDRESS + randomServerPort).resolve(ENDPOINT_GET_REGISTERED_USERS);
		ResponseEntity<Customer[]> result = restTemplate.getForEntity(uri, Customer[].class);
		assertTrue(result.getStatusCode().equals(HttpStatus.OK));
		assertNotNull(result.getBody());
		assertEquals(1, result.getBody().length);
		Stream.of(result.getBody()).forEach(cust ->{
			assertEquals("Ivan", cust.getUsername());
			assertEquals("30040", cust.getPostCodes().get(0).getCode());
		});
		
	}
	@Test
	@Order(5)
	public void testGetRegistrationForUsername() throws URISyntaxException {
		//add a few customers before searching for Ivan
		String[] userNames = { "Petar", "Todor" };
		String[] postCodes = { "20002", "21224" };

		for (int i = 0; i < 2; i++) {
			Customer customer = new Customer();
			customer.setUsername(userNames[i]);
			List<PostCode> listPostCodes = new ArrayList<>();

			PostCode postCode = new PostCode(postCodes[i], customer);
			listPostCodes.add(postCode);

			customer.setPostCodes(listPostCodes);
			makePostRequest(customer, String.class);
		}
		
		URI uri = new URI(ADDRESS + randomServerPort).resolve(String.format(ENDPOINT_GET_REGISTERED_USER_BY_NAME, "Petar"));
		ResponseEntity<Customer[]> result = restTemplate.getForEntity(uri, Customer[].class);
		assertNotNull(result.getBody());
		assertEquals(1, result.getBody().length);
		Stream.of(result.getBody()).forEach(cust ->{
			assertEquals("Petar", cust.getUsername());
			assertEquals("20002", cust.getPostCodes().get(0).getCode());
		});
		
	}

	@Test
	@Order(6)
	public void testGetPostCodesForLatestRegisteredCustomerWithUsername() throws URISyntaxException {
		//the latest registration for username Ivan is the one we did in testSuccessFulRegistration
		URI uri = new URI(ADDRESS + randomServerPort).resolve(String.format(ENDPOINT_LATEST_REGISTERED_USER_BY_NAME, "Ivan"));
		ResponseEntity<PostCode[]> result = restTemplate.getForEntity(uri, PostCode[].class);
		Stream.of(result.getBody()).forEach(postCode -> {
			assertEquals("30040", postCode.getCode());
		});
		
		//create another customer Ivan with different postCodes
		Customer customer = new Customer();
		customer.setUsername("Ivan");
		List<PostCode> listPostCodes = new ArrayList<>();
		String[]postCodes = {"21230","33411","30310"};
		for (int j = 0; j < 3; j++) {
			PostCode postCode = new PostCode(postCodes[j], customer);
			listPostCodes.add(postCode);
		}
		customer.setPostCodes(listPostCodes);
		makePostRequest(customer, String.class);
		
		//test the latest registration for username Ivan again
		ResponseEntity<PostCode[]> response = restTemplate.getForEntity(uri, PostCode[].class);
		for (int i = 0; i < response.getBody().length; i++) {
			assertEquals(postCodes[i], response.getBody()[i].getCode());
		}
	}
	
	private <T> ResponseEntity<T> makePostRequest(Customer customer, Class<T> clazz) throws URISyntaxException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Customer> entity = new HttpEntity<Customer>(customer, headers);
		return this.restTemplate.postForEntity(
				new URI(ADDRESS + randomServerPort).resolve(ENDPOINT_REGISTER_CUSTOMER), entity, clazz);
		
	}
	
	
	
}
