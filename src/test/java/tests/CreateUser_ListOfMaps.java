package tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class CreateUser_ListOfMaps {
	@Test
	public void createUserByListOfMaps() {

		RestAssured.baseURI = "https://reqres.in";

		List<Map<String, Object>> datasets = new ArrayList<>();

		for (int i = 1; i <= 10; i++) {
			Map<String, Object> place = new HashMap<>();
			place.put("name", "Academy " + i);
			place.put("job", "Job " + i); // ✅ store job
			place.put("address", "Street " + i); // ✅ store address

			datasets.add(place);
		}
		// print the dataset
		for (Map<String, Object> dataset : datasets) {
			System.out.println(dataset);
		}

		for (Map<String, Object> place : datasets) {
			// print data set
			// System.out.println(place);
			Response resp = RestAssured.given().log().all()
					.header("x-api-key", "pro_8ac7e74deb9ecb0a9a23d9b90f471ba7532bfe30cae88d183d2c3fc9d9abb0e3")
					.header("Content-Type", "application/json").body(place).when().post("/api/users").then()
					.statusCode(201).extract().response();

			Map<String, Object> responseMap = resp.jsonPath().getMap("");

			System.out.println("Thread: " + Thread.currentThread().getId() + " | Dataset: " + place.get("name")
					+ " | id: " + responseMap.get("id"));

			// 🔹 Assertions for top-level keys
			Assert.assertEquals(responseMap.get("name"), place.get("name"), "Name mismatch!");
			Assert.assertEquals(responseMap.get("job"), place.get("job"), "Job mismatch!");
			Assert.assertEquals(responseMap.get("address"), place.get("address"), "Address mismatch!");
			Assert.assertNotNull(responseMap.get("id"), "ID should be generated!");
			Assert.assertNotNull(responseMap.get("createdAt"), "createdAt timestamp should be present!");

			// 🔹 Assertions for nested _meta object
			Map<String, Object> metaMap = (Map<String, Object>) responseMap.get("_meta");
			Assert.assertEquals(metaMap.get("powered_by"), "ReqRes", "powered_by mismatch!");
			Assert.assertEquals(metaMap.get("variant"), "v1_b", "variant mismatch!");
			Assert.assertEquals(metaMap.get("context"), "legacy_success", "context mismatch!");

			// 🔹 Assertions for nested cta object inside _meta
			Map<String, Object> ctaMap = (Map<String, Object>) metaMap.get("cta");
			Assert.assertEquals(ctaMap.get("label"), "Get started", "CTA label mismatch!");
			Assert.assertEquals(ctaMap.get("url"), "https://app.reqres.in/upgrade", "CTA url mismatch!");

		}

	}

}
