package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import java.io.FileOutputStream;
import java.io.IOException;

public class DownloadFile {
	/*
	 * Unlike JSON or text responses, a file (image, PDF, ZIP, etc.) is just a
	 * stream of bytes
	 * 
	 * Rest Assured doesn’t know the “structure” of the file, so the safest way is
	 * to treat it as raw bytes.
	 */
	public static void main(String[] args) throws IOException {
		RestAssured.baseURI = "https://the-internet.herokuapp.com";

		// Call the download endpoint (example: /download/icon-apache.png)
		Response res = given().when().get("/download/test_upload.txt") // replace with actual file path
				.then().statusCode(200).extract().response();

		// Convert response to byte array
		byte[] fileBytes = res.asByteArray();

		// Save file locally
		FileOutputStream fos = new FileOutputStream("D:\\load\\downloaded_icon.png");
		fos.write(fileBytes);
		fos.close();

		System.out.println("File downloaded successfully!");

	}

}
