package stepdefs;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.ParameterType;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java8.En;
import io.restassured.RestAssured;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import utils.GlobalVariables;

import org.testng.Assert;

import java.util.*;

import static utils.ConfigFactory.getManualRequestSpec;
import static utils.ConfigFactory.getManualResponseSpec;

public class StepDefinitionSoapRestAssured implements GlobalVariables, En {

	Set<Integer> createdEmployeesIds = new HashSet<Integer>();
	String operationUsed = null;
	String expectedResult = null;
	double a = 0;
	double b = 0;
//	double resultCol1 = 0.00, resultCol2 = 0.00;

	public StepDefinitionSoapRestAssured() {
		When("^Random user (.*) number ([0-9]*) with number ([0-9]+)$", (String operation, String var1, String var2) -> {
			operationUsed = operation;
			a = Double.parseDouble(var1.replaceAll(",", "."));
			b = Double.parseDouble(var2.replaceAll(",", "."));
			Response response = RestAssured
					.given()
					.header("Content-Type", "application/soap+xml; charset=utf-8\"")
					.contentType("application/soap+xml; charset=utf-8")
					.spec(getManualRequestSpec())
					.body((myEnvelope.replaceAll("\\$\\{operation}", operation).replaceAll("\\$\\{var1}", var1).replaceAll("\\$\\{var2}", var2)))
					.when()
					.post()
					.then()
					.spec(getManualResponseSpec())
					.log().body()
					.extract().response();

			XmlPath jsXpath = new XmlPath(response.asString());//Converting string into xml path to assert
			String localResult = jsXpath.getString(operation.concat("Result")).replaceAll(",", ".");
			expectedResult = localResult;

		});

		When("^Random user do operation with the following values$", (DataTable table) -> {
			int resultCol1 = 0, resultCol2 = 0;

			List<Map<String, String>> rows = table.asMaps(String.class, String.class);
			for (Map<String, String> row : rows) {

				resultCol1 = Integer.parseInt(makeThatRockerScience(String.valueOf(row.get("operation")), String.valueOf(resultCol1),
						String.valueOf(row.get("col1"))));
				resultCol2 = Integer.parseInt(makeThatRockerScience(String.valueOf(row.get("operation")), String.valueOf(resultCol2),
						String.valueOf(row.get("col2"))));
			}
			System.out.println(resultCol1 + " " + resultCol2);
		});
	}

	@Given("User {word} number {word} with number {word}")
	public String makeThatRockerScience(String operation, String var1, String var2) {
		operationUsed = operation;
		a = Double.parseDouble(var1.replaceAll(",", "."));
		b = Double.parseDouble(var2.replaceAll(",", "."));
		Response response = RestAssured
				.given()
				.header("Content-Type", "application/soap+xml; charset=utf-8\"")
				//                .contentType("application/soap+xml; charset=utf-8")
				.spec(getManualRequestSpec())
				.body((myEnvelope.replaceAll("\\$\\{operation}", operation).replaceAll("\\$\\{var1}", var1).replaceAll("\\$\\{var2}", var2)))
				.when()
				.post()
				.then()
				.spec(getManualResponseSpec())
				.log().body()
				.extract().response();

		XmlPath jsXpath = new XmlPath(response.asString());//Converting string into xml path to assert
		String localResult = jsXpath.getString(operation.concat("Result")).replaceAll(",", ".");
		expectedResult = localResult;
		return localResult;
	}

	@Then("Result has to be mathematically correct")
	public void resultHasToBeMathematicallyCorrect() {
		switch (operationUsed) {

			case "Add":
				Assert.assertEquals((a + b), Double.parseDouble(expectedResult));
				break;

			case "Subtract":
				Assert.assertEquals((a - b), Double.parseDouble(expectedResult));
				break;

			case "Multiply":
				Assert.assertEquals((a * b), Double.parseDouble(expectedResult));
				break;

			case "Divide":
				Assert.assertEquals((a / b), Double.parseDouble(expectedResult));
				break;
		}
	}

}

