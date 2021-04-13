package apiEngine;


public class Route {
    private static final String API = "/api";
    private static final String VERSION = "/v1";
    private static final String BOOKS = "/books";
    private static final String ID = "/{id}";

    public static String getBooks() {
        return API + VERSION + BOOKS;
    }

    public static String getBook() {
        return API + VERSION + BOOKS + ID;
    }
}
