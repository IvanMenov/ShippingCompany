package com.example.demo.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

import com.example.demo.exceptions.StreamDeserializationException;
import com.example.demo.exceptions.URLConnectionException;
import com.example.demo.exceptions.URLException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {
	private static ObjectMapper mapper = createObjectMapper();
	
	private static String ZIP_CODES_URL = "https://api.zippopotam.us/us/";

	public static <T> T deserialize(String zipcode, Class<T> clazz) {
		T obj = null;
		try (InputStream stream = openStream(zipcode)) {
			obj = mapper.readValue(stream, clazz);
		} catch (IOException e) {
			throw new StreamDeserializationException("Unable to deserialize object!");
		} 
		return obj;

	}

	private static ObjectMapper createObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper;
	}

	private static InputStream openStream(String zipcode) {
		InputStream stream = null;
		String finalUrl = ZIP_CODES_URL.concat(zipcode);
			URL url;
			try {
				
				url = new URL(finalUrl);
				HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
				stream = connection.getInputStream();
			} catch (MalformedURLException e) {
				throw new URLException(String.format("Malformatted URL + %s", finalUrl));
			} catch (IOException e) {
				throw new URLConnectionException(String.format("Unable to connect to %s",finalUrl));
			}
			
		 
		return stream;

	}
}
