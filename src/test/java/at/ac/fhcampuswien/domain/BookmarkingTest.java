package at.ac.fhcampuswien.domain;

import at.ac.fhcampuswien.data.Bookmark;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookmarkingTest {

    @BeforeAll
    public static void classSetUp() {

        System.out.println(
                "This is a BookmarkingTest class method and takes place before any @Test is executed"
        );
    }

    @Test
    public void ensureAllSecureUrlsAreFound(){
        System.out.println("\t\tExecuting " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " Test");

        // Arrange
        int expectedResult = 2;
        List<Bookmark> testBookmarks = Arrays.asList(new Bookmark("http://orf.at"), new Bookmark("https://facebook.com"), new Bookmark("https://oracle.com"));
        int result = 0;

        // Act
        result = new Bookmarking().countSecureUrls(testBookmarks);

        // Assert
        assertEquals(expectedResult, result);

    }
}
