package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import java.io.File;

public class FileUpload {
	public static void main(String[] args) {
		File file = new File("D:\\load\\200Users\\content\\pages\\icon-apache.png");
		RestAssured.baseURI = "https://the-internet.herokuapp.com";
	Response res=	given().log().all().multiPart("file", file)
		.multiPart("description", "Test")
		.when()
		.post("/upload")
		.then().log().all().statusCode(200)
		.extract()
		.response();
	res.statusCode();
	}

}
