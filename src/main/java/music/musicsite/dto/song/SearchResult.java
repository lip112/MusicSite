package music.musicsite.dto.song;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchResult<T> {
    String totalCount;
    T result;

    AlbumDto albumDto;

    public SearchResult(String totalCount, T result) {
        this.totalCount = totalCount;
        this.result = result;
    }

    public SearchResult( T result, AlbumDto albumDto) {
        this.result = result;
        this.albumDto = albumDto;
    }
}
