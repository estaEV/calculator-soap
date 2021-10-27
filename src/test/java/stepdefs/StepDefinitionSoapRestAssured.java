package stepdefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import utils.GlobalVariables;
import org.testng.Assert;

import java.util.*;

import static utils.ConfigFactory.getManualRequestSpec;
import static utils.ConfigFactory.getManualResponseSpec;

public class StepDefinitionSoapRestAssured implements GlobalVariables {
    Set<Integer> createdEmployeesIds = new HashSet<Integer>();
    String operationUsed = null;
    String expectedResult = null;
    double a = 0;
    double b = 0;

    @Given("User {word} number {word} with number {word}")
    public void makeThatRockerScience(String operation, String var1, String var2) {
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

