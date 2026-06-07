package tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Demo2 {
	@Test
	public void create_Api() {
		HashMap<String, Object> userObject = new HashMap<>();
		userObject.put("name", "John");
		userObject.put("job", "leader");
		RestAssured.baseURI = "https://reqres.in";
		Response res = given()
				.header("x-api-key", "pro_8ac7e74deb9ecb0a9a23d9b90f471ba7532bfe30cae88d183d2c3fc9d9abb0e3")
				.header("X-Reqres-Env", "prod").header("Content-Type","application/json").body(userObject).when().post("/api/users").then().statusCode(201)
				.extract().response();
		System.out.println("Full Response: " + res.asString());
		Map<Object, Object> resAPI=res.jsonPath().getMap("");
		Object o1=resAPI.get("name");
		Object o2=resAPI.get("job");
		Assert.assertEquals("John",o1);
		Assert.assertEquals("leader",o2,"Job Mismatched");
		Map<Object, Object> resMeta=res.jsonPath().getMap("_meta");
		Object o4=resMeta.get("powered_by");
		Assert.assertEquals("ReqRes", o4," actual and expected did not matched");
		// And so on verify the remaining responsejson

	}
   
}
