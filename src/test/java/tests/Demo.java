package tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Demo {
	@Test
	public void addPlace() {
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
		System.out.println("Response=");

		String s1 = resp.asString();
		JsonPath js = new JsonPath(s1);
		String s = js.get("id").toString();
		System.out.println("id=" + s);

	}

}
