package at.ac.fhcampuswien.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public final class Bookmark {

    String url;
    String keyword;
    String domain;
    int rating;

    public void increaseRating() {
        rating++;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
