package stepDefinations;

import clients.BookClient;
import cucumber.ScenarioContext;
import cucumber.TestContext;
import org.junit.Assert;

public class BaseSteps {
    private ScenarioContext scenarioContext;
    private BookClient bookClient;

    public BaseSteps(TestContext testContext) {
        scenarioContext = TestContext.getScenarioContext();
        bookClient = testContext.getBookClient();
    }

    public ScenarioContext getScenarioContext() {
        return scenarioContext;
    }

    public BookClient getBookClient() {
        return bookClient;
    }

    public void assertTrue(Boolean property, String message) {
        Assert.assertTrue(message, property);
    }

    public void assertTextEqual(String actual, String expected) {
        Assert.assertEquals(actual + " and " + expected + " text not equal !", actual, expected);
    }

}