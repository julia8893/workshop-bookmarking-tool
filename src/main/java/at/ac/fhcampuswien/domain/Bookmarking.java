package at.ac.fhcampuswien.domain;

import java.util.ArrayList;
import java.util.List;

import at.ac.fhcampuswien.data.Bookmark;

public class Bookmarking {

    List<Bookmark> bookmarkList;

    public Bookmarking() {
        this.bookmarkList = new ArrayList<>();
    }

    public void add(Bookmark bookmark){
        bookmarkList.add(bookmark);
    }

}