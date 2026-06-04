package tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class AddPlaceAPi {
	@Test
	public void addPlace() throws JsonMappingException, JsonProcessingException {
		Map<String, Object> place = new HashMap<>();

		// Nested map for location
		Map<String, Double> location = new HashMap<>();
		location.put("lat", -38.383494);
		location.put("lng", 33.427362);

		// List for types
		ArrayList<String> types = new ArrayList<>();
		types.add("shoe park");
		types.add("shop");

		// Add all key-value pairs
		place.put("location", location);
		place.put("accuracy", 50);
		place.put("name", "Rahul Shetty Academy");
		place.put("phone_number", "(+91) 983 893 3937");
		place.put("address", "29, side layout, cohen 09");
		place.put("types", types);
		place.put("website", "http://rahulshettyacademy.com");
		place.put("language", "French-IN");

		// Print the HashMap
		System.out.println(place);
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		Response resp = given().body(place).queryParam("key", "qaclick123").header("ContentType", "application/json")
				.when().post("/maps/api/place/add/json").then().statusCode(200).extract().response();

//		String s1 = resp.asString();
//		JsonPath js = new JsonPath(s1);
//		String s = js.get("id").toString();
//		System.out.println("id=" + s);
//		String s2=js.get("status").toString();
//		System.out.println(s2);

		// deserialization using Jackson (built-in).
		/*
		 * Map<String, Object> responseMap = resp.as(Map.class);
		 * 
		 * // Access values from the Map System.out.println("Full Response Map: " +
		 * responseMap); String placeId = (String) responseMap.get("place_id");
		 * System.out.println("place_id = " + placeId);
		 */
		// Deserialization using direct Rest Assured shortcut. by using restAssured
		// JsonPath() method

		/*
		 * Map<String, Object> responseMap = resp.jsonPath().getMap("");
		 * 
		 * // Note: in getMap(""); nested key will be filled out get the response
		 * 
		 * System.out.println("Full Response Map: " + responseMap); String placeId =
		 * (String) responseMap.get("place_id"); System.out.println("place_id = " +
		 * placeId);
		 */
		// explicit using JsonPath class

		/*
		 * JsonPath js = new JsonPath(resp.asString()); // ✅ Get entire JSON as Map
		 * Map<String, Object> responseMap = js.getMap("");
		 * System.out.println("place_id = " + responseMap.get("place_id"));
		 */

		// using direct restAssured Jsonpath() function.
		/*
		 * Map<String, Object> responseMap = resp.jsonPath().getMap("");
		 * System.out.println("place_id = " + responseMap.get("place_id"));
		 * System.out.println("status = " + responseMap.get("status"));
		 * System.out.println("scope = " + responseMap.get("scope"));
		 * System.out.println("reference = " + responseMap.get("reference"));
		 */

		// By using Jackson ObjectMapper most important thing that is used on large
		// scale this one has been used in my recent company framework
//serialization and Deserialisation both
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> responseMap = mapper.readValue(resp.asString(), Map.class);

		System.out.println("place_id = " + responseMap.get("place_id"));
		System.out.println("status = " + responseMap.get("status"));
		System.out.println("scope = " + responseMap.get("scope"));
		System.out.println("reference = " + responseMap.get("reference"));
	}

}
