package com.example.demo;

public class Constants {
	public static final String ADDRESS = "http://localhost:";

	private static final String COMMON_ENDPOINT = "/api/v1";

	public static final String ENDPOINT_REGISTER_CUSTOMER = COMMON_ENDPOINT + "/register";

	public static final String ENDPOINT_GET_REGISTERED_USERS = COMMON_ENDPOINT + "/customers";

	public static final String ENDPOINT_GET_REGISTERED_USER_BY_NAME = ENDPOINT_GET_REGISTERED_USERS + "/%s";

	public static final String ENDPOINT_LATEST_REGISTERED_USER_BY_NAME = ENDPOINT_GET_REGISTERED_USERS + "/%s/addresses/latestregistration";

}
