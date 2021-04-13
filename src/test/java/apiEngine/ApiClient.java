package apiEngine;

import cucumber.CustomLogFilter;
import cucumber.Storage;
import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.specification.RequestSpecification;
import stepDefinations.Hooks;


public class ApiClient extends Hooks {
    public  RequestSpecification request;
    public CustomLogFilter logFilter;


    public ApiClient(String baseUrl) {
        RestAssuredConfig config = RestConfig.createConfig();
        logFilter = new CustomLogFilter();
        request = RestAssured.given().config(config).with().filter(logFilter).log().all();
        request.baseUri(baseUrl);
        request.header("Content-Type", "application/json");

    }

    public void writeStepLog() {
        Storage.getScenario().write("\n" + "API Request: " + logFilter.getRequestBuilder()
                + "\n" + "API Response: " + logFilter.getResponseBuilder());
    }

}