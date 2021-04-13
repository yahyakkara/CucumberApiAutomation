package cucumber;

import clients.BaseUrls;
import clients.BookClient;

public class TestContext {

    private static ScenarioContext scenarioContext;

    public TestContext() {
        scenarioContext = new ScenarioContext();
    }

    public BookClient getBookClient() {
        return new BookClient(BaseUrls.mockBaseUrl());
    }

    public static ScenarioContext getScenarioContext() {
        return scenarioContext;
    }

}
