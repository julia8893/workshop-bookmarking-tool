package at.ac.fhcampuswien.domain;

import at.ac.fhcampuswien.data.Bookmark;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookmarkingTest {

    Bookmarking bookmarking;

    @BeforeEach
    public void setup() {
        bookmarking = new Bookmarking();
    }

    @Test
    public void ensureAllSecureUrlsAreFound(){
        int expectedResult = 3;
        List<Bookmark> testBookmarks = Arrays.asList(new Bookmark("http://orf.at"), new Bookmark("https://facebook.com"), new Bookmark("https://oracle.com"));

        int result = new Bookmarking().countSecureUrls(testBookmarks);

        assertEquals(expectedResult, result);
    }

    @Test
    public void validateSingleUrl() {
        String urlToValidate = "https://orf.at";

        boolean result = bookmarking.validateUrl(urlToValidate);

        assertTrue(result);
    }
}
