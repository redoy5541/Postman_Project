package test;

import org.hamcrest.Matcher;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.util.concurrent.TimeUnit;

public class ReadAll {

	/*
	 * given() = all input details =(baseUri,
	 * header/s,authorization,responseTime,playload/Body) When() = submit request=
	 * httpMethod(endpoint) then()= response validation(statusCode,
	 * header/s,responseTime, response payload/Body)
	 * 
	 * http method: GET  endPoint:
	 * http://techfios.com/api-prod/api/product/read.php; header: Content-Type =
	 * application/json; charset=UTF-8; Authorization:basic auth; username:
	 * demo@techfios.com; password: abc123;
	 */

	@Test
	public void readAllProducts() {

		Response response =

				given()
				        .baseUri("http://techfios.com/api-prod/api/product")
						.header("Content-Type", "application/json; charset=UTF-8").auth().preemptive()
						.basic("demo@techfios.com", "abc123").

			    when()
						.get("/read.php").

		        then()

						.extract().response();

		long responseTime = response.getTimeIn(TimeUnit.MILLISECONDS);

		System.out.println("Response Time: " + responseTime);

		if (responseTime <= 2500) {

			System.out.println("Response time is within range.");

		} else {

			System.out.println("Respinse tie is out of range.");

		}

		int responseStatusCode = response.getStatusCode();
		System.out.println("Response status Code : " + responseStatusCode);
		Assert.assertEquals(responseStatusCode, 200);

		String responseHeaderContentTipe = response.getHeader("Content-Type");
		System.out.println("Response header ContentType: " + responseHeaderContentTipe);
		Assert.assertEquals(responseHeaderContentTipe, "application/json; charset=UTF-8");

		String responseBody = response.getBody().asString();
		System.out.println("Response Body : " + responseBody);

		JsonPath jp = new JsonPath(responseBody);

		String firstProductId = jp.getString("records[0].id");

		System.out.println("First product Id: " + firstProductId);

		if (firstProductId != null) {

			System.out.println("Products list not empty");

		} else {

			System.err.println("Products list is empty");

		}

	}

}
