package at.ac.fhcampuswien.domain;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import at.ac.fhcampuswien.data.Bookmark;

public class Bookmarking {

    List<Bookmark> bookmarkList;

    public Bookmarking() {
        this.bookmarkList = new ArrayList<>();
    }

    public void add(Bookmark bookmark) throws InvalidUrlException {
        if(validateUrl(bookmark.getUrl())) {
            if(checkUrlDuplication(bookmark)){
                bookmark.increaseRating();
                return;
            }
            bookmarkList.add(bookmark);
            return;
        }
        throw new InvalidUrlException();
    }

    public boolean validateUrl(String url) {
        try {
            new URL(url);
        } catch (MalformedURLException e) {
            return false;
        }
        return true;
    }

    private boolean checkUrlDuplication(Bookmark bookmarkToCheck){
        return bookmarkList.stream().anyMatch(bookmark -> bookmark.getUrl().equals(bookmarkToCheck.getUrl()));
    }
}