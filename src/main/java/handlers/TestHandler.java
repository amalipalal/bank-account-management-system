package handlers;

import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

public class TestHandler {

    public void run() {
        System.out.println("\nRunning tests with JUnit\n");

        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(
                        selectPackage("services"),
                        selectPackage("models")
                )
                .build();

        Launcher launcher = LauncherFactory.create();

        TestExecutionListener listener = new TestExecutionListener() {
            @Override
            public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult result) {

                if (testIdentifier.isTest()) {
                    String name = testIdentifier.getDisplayName();

                    String status = switch (result.getStatus()) {
                        case SUCCESSFUL -> "PASSED";
                        case FAILED     -> "FAILED";
                        case ABORTED    -> "ABORTED";
                    };

                    System.out.println("TEST: " + name + "..............." + status);
                }
            }
        };

        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);

        System.out.println("\nTests Successfully Run\n");
    }
}
