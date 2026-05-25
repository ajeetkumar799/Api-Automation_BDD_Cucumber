package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

public class CreateUserAPI {
	// serialization using multiple dataSet using dataProvide
	@DataProvider(name = "placeData")
	public Object[][] getData() {
		return new Object[][] { { "Academy1", "Street1", "Job1" }, { "Academy2", "Street2", "Job2" },
				{ "Academy3", "Street3", "Job3" }, { "Academy4", "Street4", "Job4" }, { "Academy5", "Street5", "Job5" },
				{ "Academy6", "Street6", "Job6" }, { "Academy7", "Street7", "Job7" }, { "Academy8", "Street8", "Job8" },
				{ "Academy9", "Street9", "Job9" }, { "Academy10", "Street10", "Job10" } };
	}

	@Test(dataProvider = "placeData")
	public void CreateUser(String name, String job, String address) {
		Map<String, Object> userDetails = new HashMap<>();
		userDetails.put("name", name);
		userDetails.put("job", job);
		userDetails.put("accuracy", 50);
		userDetails.put("address", address);
		RestAssured.baseURI = "https://reqres.in";

		Response resp = given().log().all().header("Content-Type", "application/json")
				.header("x-api-key", "pro_8ac7e74deb9ecb0a9a23d9b90f471ba7532bfe30cae88d183d2c3fc9d9abb0e3")
				.body(userDetails).when().post("/api/users").then().statusCode(201).extract().response();
		System.out.println("Full Response: " + resp.asString());
		Map<String, Object> responseMap = resp.jsonPath().getMap("");
		// ✅ Assertions for each dataset
		Assert.assertEquals(responseMap.get("name"), name, "Name mismatch!");
		Assert.assertEquals(responseMap.get("address"), address, "Job mismatch!");
		Assert.assertNotNull(responseMap.get("id"), "ID should be generated!");
		Assert.assertNotNull(responseMap.get("createdAt"), "createdAt timestamp should be present!");

		System.out.println("✔ Test passed for dataset: " + name + " | " + address);
	}

}
