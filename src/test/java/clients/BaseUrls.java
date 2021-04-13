package clients;

public class BaseUrls {

    private static final String BASE_URL = "http://localhost:3474";
    private static final String BOOK_URL = "http://bookStoreTYcase.com";

    public static String mockBaseUrl(){
        return BASE_URL;
    }

    public static String BookBaseUrl(){
        return BOOK_URL;
    }
}
