package stepDefinations;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.core.Options;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import com.github.tomakehurst.wiremock.stubbing.Scenario;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public class MockServer {
    public WireMockServer wireMockServer;

    public void setup() {
        Options options = wireMockConfig()
                .port(3474)
                .notifier(new ConsoleNotifier(false))
                .extensions(new ResponseTemplateTransformer(true));

        wireMockServer = new WireMockServer(options);
        wireMockServer.start();
        setupStub();
    }

    public void teardown() {
        wireMockServer.stop();
    }

    public void setupStub() {
        stubForGetEmptyBooks();
        stubForValidAddBooks();
        stubForBookList();
        stubForBookAlreadyAdded();
        stubForAddBookTittleMissing();
        stubForAddBookAuthorMissing();
        stubForGetBook();
        stubForGetBookError();
        stubForAddBookIdError();
    }

    public void stubForGetEmptyBooks() {
        wireMockServer.stubFor(get(urlEqualTo("/api/v1/books"))
                .inScenario("addBook")
                .whenScenarioStateIs(Scenario.STARTED)
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("emptyBooks.json")));
    }

    public void stubForBookList() {
        wireMockServer.stubFor(get(urlEqualTo("/api/v1/books"))
                .inScenario("addBook")
                .whenScenarioStateIs("itemAdded")
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("books.json")));
    }

    public void stubForValidAddBooks() {
        wireMockServer.stubFor(put(urlEqualTo("/api/v1/books"))
                .inScenario("addBook")
                .willSetStateTo("itemAdded")
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(201)
                        .withBodyFile("bookAdded.json")));
    }

    public void stubForBookAlreadyAdded() {
        wireMockServer.stubFor(put(urlEqualTo("/api/v1/books"))
                .inScenario("addBook")
                .whenScenarioStateIs("itemAdded")
                .withRequestBody(matchingJsonPath("[?(@.title=='SRE 101' || @.author == 'John Smith')]"))
                .willReturn(serverError()
                        .withStatus(400)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("similarBookError.json")));
    }

    public void stubForAddBookTittleMissing() {
        wireMockServer.stubFor(put(urlEqualTo("/api/v1/books"))
                .withRequestBody(matchingJsonPath("[?(@.title=='')]"))
                .willReturn(serverError()
                        .withStatus(400)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("missingTitleError.json")));
    }

    public void stubForAddBookAuthorMissing() {
        wireMockServer.stubFor(put(urlEqualTo("/api/v1/books"))
                .withRequestBody(matchingJsonPath("[?(@.author=='')]"))
                .willReturn(serverError()
                        .withStatus(400)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("missingAuthorError.json")));
    }

    public void stubForAddBookIdError() {
        wireMockServer.stubFor(put(urlEqualTo("/api/v1/books"))
                .withRequestBody(matchingJsonPath("$.id"))
                .willReturn(serverError()
                        .withStatus(400)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("idReadOnlyError.json")));
    }

    public void stubForGetBook() {
        //mock for id lower than 10
        wireMockServer.stubFor(get(urlPathMatching("/api/v1/books/(10|[1-9])$"))
                .inScenario("addBook")
                .whenScenarioStateIs("itemAdded")
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBodyFile("getBook.json")));
    }

    public void stubForGetBookError() {
        wireMockServer.stubFor(get(urlPathMatching("/api/v1/books/[9-9]|[1-9]\\d+$"))
                .willReturn(serverError()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(404)));
    }

    public static void main(String[] args) {
        MockServer mockServer = new MockServer();
        mockServer.setup();
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mockServer.wireMockServer.resetScenarios();

    }

}