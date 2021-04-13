package stepDefinations;

import cucumber.Storage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {
    static MockServer mockServer = new MockServer();

    @Before(order = 1)
    public void getScenario(Scenario scenario) {
        Storage.putScenario(scenario);
    }

    @Before(order = 2)
    public void startMock() {
        mockServer.setup();
    }

    @After
    public void closeMock() {
        mockServer.teardown();
    }
}