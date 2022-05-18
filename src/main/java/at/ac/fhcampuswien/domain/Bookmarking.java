package at.ac.fhcampuswien.domain;

import java.util.ArrayList;
import java.util.List;

import at.ac.fhcampuswien.data.Bookmark;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class Bookmarking {

    List<Bookmark> bookmarks;


    public Bookmarking() {
        this.bookmarks = new ArrayList<>();
    }

    public void addBookmark(Bookmark bookmark){
        this.bookmarks.add(bookmark);
    }

    public int countSecureUrls(List<Bookmark> url) {
        return url.size();
    }


    public boolean validateUrl(String url) {
        return true;
    }
}
