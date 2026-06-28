package tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class UpdateUser {
	@Test
	public static void updateUserWithDifferentSetOfData() throws JsonProcessingException {
		RestAssured.baseURI = "https://reqres.in";
		ObjectMapper objm = new ObjectMapper();
		List<Map<String, Object>> dataSet = new ArrayList<>();
		for (int i = 1; i <= 4; i++) {
			HashMap<String, Object> updateUser = new HashMap<String, Object>();
			updateUser.put("name", "Ram" + i);
			updateUser.put("job", "Software Engineer" + i);
			dataSet.add(updateUser);
		}
		for (Map<String, Object> updateUser1 : dataSet) {
			String jsonPayLoad = objm.writeValueAsString(updateUser1);
			Response res = RestAssured.given().log().all().header("Content-Type", "application/json")
					.header("x-api-key", "pro_8ac7e74deb9ecb0a9a23d9b90f471ba7532bfe30cae88d183d2c3fc9d9abb0e3")
					.body(jsonPayLoad).when().put("/api/users/2").then().statusCode(200).extract().response();
			Map<String, Object> respMap = res.jsonPath().getMap("");
			Object name = respMap.get("name");
			Assert.assertEquals(updateUser1.get("name"), name);
			Map<String, Object> mapMeta = (Map<String, Object>) respMap.get("_meta");

			Object powerdBy = mapMeta.get("powered_by");
			Assert.assertEquals("ReqRes", powerdBy);
			Assert.assertEquals(mapMeta.get("context"), "legacy_success", "context mismatch!");
			Map<String, Object> mapCta = (Map<String, Object>) mapMeta.get("cta");
			Assert.assertEquals(mapCta.get("label"), "Get started", "cta not found");
			System.out.println("Demo test");
			System.out.println("Demo test");
			System.out.println("Demo test");
			System.out.println("Demo test");
		}

	}

}
