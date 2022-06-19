package at.ac.fhcampuswien.domain;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import at.ac.fhcampuswien.data.Bookmark;

public class Bookmarking {

    List<Bookmark> bookmarkList;
    final String SECURE_URL = "https";

    public Bookmarking() {
        this.bookmarkList = new ArrayList<>();
    }

    public void add(Bookmark bookmark) throws InvalidUrlException {
        if (validateUrl(bookmark.getUrl())) {
            if (checkUrlDuplication(bookmark)) {
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

    private boolean checkUrlDuplication(Bookmark bookmarkToCheck) {
        return bookmarkList.stream().anyMatch(bookmark -> bookmark.getUrl().equals(bookmarkToCheck.getUrl()));
    }

    public int getSecureUrlAmount() throws MalformedURLException {
        int securedAmount = 0;
        for (Bookmark bookmark : bookmarkList) {
            if (new URL(bookmark.getUrl()).getProtocol().equals(SECURE_URL))
                securedAmount++;
        }
        return securedAmount;
    }

    public List<Bookmark> getBookmarkByDomain(String domainToFilter) {
        return this.bookmarkList.stream().filter(bookmark -> bookmark.getDomain().contains(domainToFilter)).collect(Collectors.toList());
    }

    public List<Bookmark> filterByKeywords(List<String> filterKeywords) {
        List<Bookmark> filteredBookmarks = new ArrayList<>();
        for (Bookmark bookmark : bookmarkList) {
            for (String filterKeyword : filterKeywords) {
                if (bookmark.getKeyword().equals(filterKeyword))
                    filteredBookmarks.add(bookmark);
            }
        }
        return filteredBookmarks;
    }

    public void removeTagFromBookmark(Bookmark bookmark) throws InvalidUrlException {
        Bookmark foundBookmark = findBookmarkByUrl(bookmark.getUrl());
        foundBookmark.setKeyword("");
    }

    public void removeBookmark(Bookmark bookmark) {
        this.bookmarkList.remove(bookmark);
    }

    private Bookmark findBookmarkByUrl(String url) throws InvalidUrlException {
        return this.bookmarkList.stream().findFirst().filter(bookmark -> bookmark.getUrl().equals(url)).orElseThrow(InvalidUrlException::new);
    }

    public List<Bookmark> sortByHighestRating() {
        return this.bookmarkList.stream().sorted(Comparator.comparing(Bookmark::getRating).reversed()).collect(Collectors.toList());
    }
}
