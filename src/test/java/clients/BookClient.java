package clients;

import apiEngine.ApiClient;
import apiEngine.IRestResponse;
import apiEngine.RestResponse;
import apiEngine.Route;
import apiEngine.models.request.BookRequest;
import apiEngine.models.request.InvalidBookRequest;
import apiEngine.models.response.Book;
import apiEngine.models.response.BooksResponse;
import io.restassured.response.Response;

public class BookClient extends ApiClient {

    public BookClient(String baseUrl) {
        super(baseUrl);
    }

    public IRestResponse<BooksResponse> getBooks() {
        Response response = request
                .get(Route.getBooks());
        writeStepLog();
        return new RestResponse(BooksResponse.class, response);
    }


    public IRestResponse<Book> addBook(BookRequest bookRequest) {
        Response response = request.
                body(bookRequest)
                .put(Route.getBooks());
        writeStepLog();
        return new RestResponse<>(Book.class, response);
    }

    public IRestResponse<Book> addBook(InvalidBookRequest bookRequest) {
        Response response = request.
                body(bookRequest)
                .put(Route.getBooks());
        writeStepLog();
        return new RestResponse<>(Book.class, response);
    }

    public IRestResponse<Book> getBook(int id) {
        Response response = request
                .pathParam("id", id)
                .get(Route.getBook());
        writeStepLog();
        return new RestResponse<>(Book.class, response);
    }

}
