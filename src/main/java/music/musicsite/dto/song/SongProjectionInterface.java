package music.musicsite.dto.song;


//Jpql로 할때 Mapper용 인터페이스
public interface SongProjectionInterface {
    String getArtist();

    String getTitle();

    Long getRequestCount();
}
