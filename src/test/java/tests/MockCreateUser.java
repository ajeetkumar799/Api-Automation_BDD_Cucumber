package tests;

import static io.restassured.RestAssured.given;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

public class MockCreateUser {
	@Test
	public void apiDbValidationTest() throws Exception {
		// Step 1: Hit API
		Response response = given().log().all().contentType("application/json")
				.body("{\r\n"
						+ "    \"name\": \"Ajeet1234569\",\r\n"
						+ "    \"email\": \"ajeet.sin1234560@example.com\",\r\n"
						+ "    \"age\": \"34\"\r\n"
						+ "}")
				.when().post("http://localhost:4000/users").then().log().all().statusCode(200).extract().response();

		// Step 2: Validate API response message
		String message = response.jsonPath().getString("message");
		Assert.assertEquals(message, "User synced to JSON Server + MySQL");

		// Step 3: Connect to DB using JDBC
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "root", "Aje@123456kum");
		Statement stmt = con.createStatement();

		// Step 4: Query DB directly for inserted user
		ResultSet rs = stmt.executeQuery("SELECT name, email, age FROM users WHERE name='Ajeet123456'");

		// Step 5: Validate DB values
		if (rs.next()) {
			Assert.assertEquals(rs.getString("name"), "Ajeet123456");
			Assert.assertEquals(rs.getString("email"), "ajeet.sin123456@example.com");
			Assert.assertEquals(rs.getInt("age"), 34);
		} else {
			Assert.fail("No record found in DB for name: Ajeet12345");
		}

		con.close();
	}

}
