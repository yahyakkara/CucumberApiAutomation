package cucumber;

import io.cucumber.java.Scenario;

import java.util.HashMap;

public class Storage {
    private static final HashMap<Thread, Scenario> map = new HashMap<>();

    public static void putScenario(Scenario scenario) {
        map.put(Thread.currentThread(), scenario);
    }

    public static Scenario getScenario() {
        return map.get(Thread.currentThread());
    }
}



