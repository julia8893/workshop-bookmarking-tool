package at.ac.fhcampuswien.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class Bookmark {

    String url;
    String tag;
}
