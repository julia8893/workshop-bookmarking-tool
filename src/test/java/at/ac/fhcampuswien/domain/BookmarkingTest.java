package at.ac.fhcampuswien.domain;

import at.ac.fhcampuswien.data.Bookmark;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.*;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookmarkingTest {

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
        Bookmark bookmark = Bookmark.builder().url("https://orf.at").keyword("aut").build();
        Bookmark bookmark1 = Bookmark.builder().url("https://youtube.com").keyword("aut").build();
        Bookmark bookmark2 = Bookmark.builder().url("http://facebook.at").keyword("social").build();

        Bookmarking bookmarking = new Bookmarking();
        bookmarking.add(bookmark);
        bookmarking.add(bookmark1);
        bookmarking.add(bookmark2);
        int result = bookmarking.getSecureUrlAmount();

        assertEquals(2, result);
    }

    @Test
    public void ensureNewBookmarkGetsAssociatedBySameDomain() throws InvalidUrlException {
        Bookmark bookmark = Bookmark.builder().url("https://orf.at").keyword("aut").domain("orf").build();
        Bookmark youtube1 = Bookmark.builder().url("https://youtube.com/xy").keyword("aut").domain("youtube").build();
        Bookmark youtube2 = Bookmark.builder().url("https://youtube.com/xyz").keyword("aut").domain("youtube").build();

        Bookmarking bookmarking = new Bookmarking();
        bookmarking.add(bookmark);
        bookmarking.add(youtube1);
        bookmarking.add(youtube2);

        List<Bookmark> result = bookmarking.getBookmarkByDomain("youtube");

        assertEquals(2, result.size());
    }

    @Test
    public void ensureBookmarksAreFilteredByOneKeyword() throws InvalidUrlException {
        Bookmark bookmark = Bookmark.builder().url("https://orf.at").keyword("aut").build();
        Bookmark bookmark1 = Bookmark.builder().url("https://youtube.com").keyword("aut").build();
        Bookmark bookmark2 = Bookmark.builder().url("http://facebook.at").keyword("social").build();

        Bookmarking bookmarking = new Bookmarking();
        bookmarking.add(bookmark);
        bookmarking.add(bookmark1);
        bookmarking.add(bookmark2);
        List<String> filterKeywords = new ArrayList<>();
        filterKeywords.add("aut");
        int result = bookmarking.filterByKeywords(filterKeywords).size();

        assertEquals(2, result);
    }

    @Test
    public void ensureBookmarksAreFilteredByManyKeywords() throws InvalidUrlException {
        Bookmark bookmark = Bookmark.builder().url("https://orf.at").keyword("aut").build();
        Bookmark bookmark1 = Bookmark.builder().url("https://youtube.com").keyword("aut").build();
        Bookmark bookmark2 = Bookmark.builder().url("http://facebook.at").keyword("social").build();
        Bookmark bookmark3 = Bookmark.builder().url("http://oracle.com").keyword("programming").build();

        Bookmarking bookmarking = new Bookmarking();
        bookmarking.add(bookmark);
        bookmarking.add(bookmark1);
        bookmarking.add(bookmark2);
        bookmarking.add(bookmark3);
        List<String> filterKeywords = new ArrayList<>();
        filterKeywords.add("aut");
        filterKeywords.add("programming");
        int result = bookmarking.filterByKeywords(filterKeywords).size();

        assertEquals(3, result);
    }

    @Test
    public void ensureTagIsRemovedFromBookmark() throws InvalidUrlException{
        Bookmark bookmark = Bookmark.builder().url("https://youtube.com").keyword("removeThis").build();

        Bookmarking bookmarking = new Bookmarking();
        bookmarking.add(bookmark);

        bookmarking.removeTagFromBookmark(bookmark);

        assertEquals("", bookmarking.bookmarkList.get(0).getKeyword());
    }

    @Test
    public void ensureBookmarkIsRemoved() throws InvalidUrlException {
        Bookmark bookmark = Bookmark.builder().url("https://youtube.com").keyword("removeThis").build();
        Bookmark bookmark2 = Bookmark.builder().url("https://orf.com").keyword("removeThis").build();

        Bookmarking bookmarking = new Bookmarking();
        bookmarking.add(bookmark);
        bookmarking.add(bookmark2);

        bookmarking.removeBookmark(bookmark);

        assertEquals(1, bookmarking.bookmarkList.size());
    }

    @Test
    public void ensureBookmarksAreSortedByHighestRating() throws InvalidUrlException {
        Bookmark youtube = Bookmark.builder().url("https://youtube.com").keyword("youtube").build();
        Bookmark orf = Bookmark.builder().url("https://orf.com").keyword("orf").build();
        Bookmark facebook = Bookmark.builder().url("https://facebook.com").keyword("facebook").build();

        Bookmarking bookmarking = new Bookmarking();
        bookmarking.add(orf);
        bookmarking.add(youtube);
        bookmarking.add(youtube);
        bookmarking.add(youtube);
        bookmarking.add(youtube);
        bookmarking.add(youtube);
        bookmarking.add(youtube);
        bookmarking.add(facebook);
        bookmarking.add(facebook);
        bookmarking.add(facebook);

        List<Bookmark> result = bookmarking.sortByHighestRating();

        assertEquals("youtube", result.get(0).getKeyword());
        assertEquals("facebook", result.get(1).getKeyword());
        assertEquals("orf", result.get(2).getKeyword());
        //
    }
}
