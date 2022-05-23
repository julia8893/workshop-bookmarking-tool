package at.ac.fhcampuswien.domain;

import at.ac.fhcampuswien.data.Bookmark;
import org.junit.jupiter.api.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookmarkingTest {

    //* As a user I want to bookmark a URL
    @Test
    public void ensureUrlIsBookmarked() throws InvalidUrlException {
        Bookmark bookmark = Bookmark.builder().url("https://orf.at").build();

        Bookmarking bookmarking = new Bookmarking();
        bookmarking.add(bookmark);

        assertEquals(1, bookmarking.bookmarkList.size());
    }

    @Test
    public void ensureTwoUrlsAreBookmarked() throws InvalidUrlException {
        Bookmark bookmark = Bookmark.builder().url("https://orf.at").build();
        Bookmark bookmark2 = Bookmark.builder().url("https://orf2.at").build();

        Bookmarking bookmarking = new Bookmarking();
        bookmarking.add(bookmark);
        bookmarking.add(bookmark2);

        assertEquals(2, bookmarking.bookmarkList.size());
    }

    @Test
    public void ensureInvalidUrlIsNotBookmarked() {
        Bookmark bookmark = Bookmark.builder().url("orf.at").build();

        Bookmarking bookmarking = new Bookmarking();

        assertThrows(InvalidUrlException.class, () -> bookmarking.add(bookmark));
    }

    //  * URLs must be valid
    @Test
    public void ensureThatUrlIsValid() {
        String url = "https://orf.at";

        Bookmarking bookmarking = new Bookmarking();
        boolean result = bookmarking.validateUrl(url);

        assertTrue(result);
    }

    @Test
    public void ensureThatUrlIsInvalid() {
        String url = "orf.whatever";

        Bookmarking bookmarking = new Bookmarking();
        boolean result = bookmarking.validateUrl(url);

        assertFalse(result);
    }

    @Test
    public void ensureThatEmptyUrlIsInvalid() {
        String url = "";

        Bookmarking bookmarking = new Bookmarking();
        boolean result = bookmarking.validateUrl(url);

        assertFalse(result);
    }

    //* As a user I want to be able to Tag a URL with a keyword
    @Test
    public void ensureAUrlIsTagged() throws InvalidUrlException {
        Bookmark bookmark = Bookmark.builder()
                .url("https://orf.at")
                .keyword("aut")
                .build();

        Bookmarking bookmarking = new Bookmarking();
        bookmarking.add(bookmark);

        assertEquals(1, bookmarking.bookmarkList.size());
    }

    @Test
    public void ensureRatingIsIncreasedOnDuplicateBookmark() throws InvalidUrlException {
        Bookmark bookmark = Bookmark.builder()
                .url("https://orf.at")
                .keyword("aut")
                .build();

        Bookmarking bookmarking = new Bookmarking();
        bookmarking.add(bookmark);
        bookmarking.add(bookmark);

        int result = bookmark.getRating();
        assertEquals(1, result);
    }

    @Test
    public void ensureDuplicateUrlIsNotBookmarked() throws InvalidUrlException {
        Bookmark bookmark = Bookmark.builder()
                .url("https://orf.at")
                .keyword("aut")
                .build();

        Bookmarking bookmarking = new Bookmarking();
        bookmarking.add(bookmark);
        bookmarking.add(bookmark);

        int result = bookmarking.bookmarkList.size();
        assertEquals(1, result);
    }

    @Test
    public void ensureThatNumberOfSecuredUrlsIsReturned() throws InvalidUrlException, MalformedURLException {

        // Arrange
        Bookmark bookmark = Bookmark.builder().url("https://orf.at").keyword("aut").build();
        Bookmark bookmark1 = Bookmark.builder().url("https://youtube.com").keyword("aut").build();
        Bookmark bookmark2 = Bookmark.builder().url("http://facebook.at").keyword("social").build();

        // Act
        Bookmarking bookmarking = new Bookmarking();
        bookmarking.add(bookmark);
        bookmarking.add(bookmark1);
        bookmarking.add(bookmark2);
        int result = bookmarking.getSecureUrlAmount();

        // Assert
        assertEquals(2, result);
    }

    @Test
    public void ensureNewBookmarkGetsAssociatedBySameDomain() throws InvalidUrlException {

        // Arrange
        Bookmark bookmark = Bookmark.builder().url("https://orf.at").keyword("aut").build();
        Bookmark bookmark1 = Bookmark.builder().url("https://youtube.com").keyword("aut").build();
        Bookmark bookmark2 = Bookmark.builder().url("http://facebook.at").keyword("social").build();
        Bookmark bookmark3 = Bookmark.builder().url("http://derstandard.at").keyword("social").build();

        // Act
        Bookmarking bookmarking = new Bookmarking();
        bookmarking.add(bookmark);
        bookmarking.add(bookmark1);
        bookmarking.add(bookmark2);
        bookmarking.add(bookmark3);
        boolean result = bookmarking.associateDomain();

        // Assert
        assertTrue(result);
    }

    @Test
    public void ensureBookmarksAreFilteredByOneKeyword() throws InvalidUrlException {

        // Arrange
        Bookmark bookmark = Bookmark.builder().url("https://orf.at").keyword("aut").build();
        Bookmark bookmark1 = Bookmark.builder().url("https://youtube.com").keyword("aut").build();
        Bookmark bookmark2 = Bookmark.builder().url("http://facebook.at").keyword("social").build();

        // Act
        Bookmarking bookmarking = new Bookmarking();
        bookmarking.add(bookmark);
        bookmarking.add(bookmark1);
        bookmarking.add(bookmark2);
        List<String> filterKeywords = new ArrayList<>();
        filterKeywords.add("aut");
        int result = bookmarking.filterByKeywords(filterKeywords).size();

        // Assert
        assertEquals(2, result);

    }

    @Test
    public void ensureBookmarksAreFilteredByManyKeywords() throws InvalidUrlException {

        // Arrange
        Bookmark bookmark = Bookmark.builder().url("https://orf.at").keyword("aut").build();
        Bookmark bookmark1 = Bookmark.builder().url("https://youtube.com").keyword("aut").build();
        Bookmark bookmark2 = Bookmark.builder().url("http://facebook.at").keyword("social").build();
        Bookmark bookmark3 = Bookmark.builder().url("http://oracle.com").keyword("programming").build();

        // Act
        Bookmarking bookmarking = new Bookmarking();
        bookmarking.add(bookmark);
        bookmarking.add(bookmark1);
        bookmarking.add(bookmark2);
        bookmarking.add(bookmark3);
        List<String> filterKeywords = new ArrayList<>();
        filterKeywords.add("aut");
        filterKeywords.add("programming");
        int result = bookmarking.filterByKeywords(filterKeywords).size();

        // Assert
        assertEquals(3, result);

    }
}
