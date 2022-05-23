package at.ac.fhcampuswien.domain;

import at.ac.fhcampuswien.data.Bookmark;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class BookmarkingTest {

    //* As a user I want to bookmark a URL
    @Test
    public void ensureUrlIsBookmarked() {
        Bookmark bookmark = Bookmark.builder().url("orf.at").build();

        Bookmarking bookmarking = new Bookmarking();
        bookmarking.add(bookmark);

        assertEquals(1, bookmarking.bookmarkList.size());
    }
}
