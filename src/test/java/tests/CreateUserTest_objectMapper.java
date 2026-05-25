package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.*;

public class CreateUserTest_objectMapper {
	/*
	 * HashMap is a Java collection for storing key–value pairs in memory, while
	 * ObjectMapper is a Jackson class used to convert Java objects to  Json  object and from
	 * JSON Object to Java Object . HashMap is about data storage efficiency, whereas ObjectMapper is about
	 * data format conversion.
	 */

	@Test
	public void createUserByListOfMaps() throws Exception {
		RestAssured.baseURI = "https://reqres.in";

		ObjectMapper mapper = new ObjectMapper(); // ✅ Jackson ObjectMapper

		List<Map<String, Object>> datasets = new ArrayList<>();

		for (int i = 1; i <= 100; i++) {
			Map<String, Object> place = new HashMap<>();
			place.put("name", "Academy " + i);
			place.put("job", "Job " + i);
			place.put("address", "Street " + i);
			datasets.add(place);
		}

		for (Map<String, Object> place : datasets) {
			// Convert Map -> JSON string
			String jsonPayload = mapper.writeValueAsString(place);

			Response resp = RestAssured.given().log().all()
					.header("x-api-key", "pro_8ac7e74deb9ecb0a9a23d9b90f471ba7532bfe30cae88d183d2c3fc9d9abb0e3")
					.header("Content-Type", "application/json").body(jsonPayload) // ✅ send JSON string
					.when().post("/api/users").then().statusCode(201).extract().response();

			Map<String, Object> responseMap = resp.jsonPath().getMap("");
			System.out.println("Thread: " + Thread.currentThread().getId() + " | Dataset: " + place.get("name")
					+ " | id: " + responseMap.get("id"));
		}
	}
}
