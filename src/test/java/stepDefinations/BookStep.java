package stepDefinations;

import apiEngine.IRestResponse;
import apiEngine.models.request.BookRequest;
import apiEngine.models.request.InvalidBookRequest;
import apiEngine.models.response.Book;
import apiEngine.models.response.BooksResponse;
import cucumber.TestContext;
import enums.Context;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

@SuppressWarnings("unchecked")
public class BookStep extends BaseSteps {

    public BookStep(TestContext testContext) {
        super(testContext);
    }

    @When("User get the book list")
    public void user_get_the_book_list() {
        IRestResponse<BooksResponse> booksResponse = getBookClient().getBooks();
        List<Book> bookList = booksResponse.getBody().getBooks();
        getScenarioContext().setContext(Context.BOOKS_RES, booksResponse);
        getScenarioContext().setContext(Context.BOOK_LIST, bookList);
    }

    @When("User add {string} {string} to list")
    public void user_add_selected_book_to_store(String title, String author) {
        BookRequest bookRequest = new BookRequest(title, author);
        IRestResponse<Book> bookResponse = getBookClient().addBook(bookRequest);
        getScenarioContext().setContext(Context.ADDED_BOOK, bookResponse);
    }

    @Then("user verify selected book is added to list")
    public void user_verify_selected_book_is_added_to_list() {
        List<Book> bookList = (List<Book>) getScenarioContext().getContext(Context.BOOK_LIST);

        IRestResponse<Book> addBookResponse = (IRestResponse<Book>) getScenarioContext().getContext(Context.ADDED_BOOK);

        String addedBookTitle = addBookResponse.getBody().getTitle();
        String addedBookAuthor = addBookResponse.getBody().getAuthor();

        Book listedBook = bookList.get(0);
        String listedBookTitle = listedBook.getTitle();
        String listedBookAuthor = listedBook.getAuthor();

        assertTextEqual(addedBookTitle, listedBookTitle);
        assertTextEqual(addedBookAuthor, listedBookAuthor);
    }

    @Then("Verify Book list is empty")
    public void verify_Book_list_is_empty() {
        List<Book> bookList = (List<Book>) getScenarioContext().getContext(Context.BOOK_LIST);
        assertTrue(bookList.size() == 0, "Book list should be empty");
    }

    @Then("User should get similar book already added error")
    public void user_should_get_error_similar_book_already_added_error() {
        IRestResponse<Book> addBookResponse = (IRestResponse<Book>) getScenarioContext().getContext(Context.ADDED_BOOK);
        String actualMessage = addBookResponse.getBody().getStatus().getMessage();
        String expectedMessage = "Another book with similar title and author already exists.";
        assertTextEqual(expectedMessage, actualMessage);
    }

    @Then("User should get {string} message from add books service")
    public void user_should_get_message_from_books_service(String message) {
        IRestResponse<Book> addBookResponse = (IRestResponse<Book>) getScenarioContext().getContext(Context.ADDED_BOOK);
        String actualMessage = addBookResponse.getBody().getStatus().getMessage();
        assertTextEqual(message, actualMessage);

    }

    @When("User add {int} {string} {string} to list")
    public void user_add_to_list(int id, String title, String author) {
        InvalidBookRequest invalidBookRequest = new InvalidBookRequest(title, author, id);
        IRestResponse<Book> bookResponse = getBookClient().addBook(invalidBookRequest);
        getScenarioContext().setContext(Context.ADDED_BOOK, bookResponse);

    }

    @When("User navigate the book with {int}")
    public void user_navigate_the_book_with(Integer id) {
        IRestResponse<Book> listedBookResponse = getBookClient().getBook(id);
        getScenarioContext().setContext(Context.LISTED_BOOK_RESPONSE, listedBookResponse);
    }

    @Then("User should get URL not found error")
    public void user_should_get_URL_not_found_error() {
        IRestResponse<Book> listedBook =
                (IRestResponse<Book>) getScenarioContext().getContext(Context.LISTED_BOOK_RESPONSE);
        int statusCode = listedBook.getStatusCode();
        assertTrue(statusCode == 404, "Status code should be 404");
    }

    @Then("User can navigate book information")
    public void user_can_navigate_book_information() {
        IRestResponse<Book> listedBook =
                (IRestResponse<Book>) getScenarioContext().getContext(Context.LISTED_BOOK_RESPONSE);
        Book book = listedBook.getBody();
        int code = book.getStatus().getCode();
        assertTrue(code ==200, "Books response should be 200 !");
    }

}