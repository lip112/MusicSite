package music.musicsite.dto.music;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlbumDto {
    private String albumId;
    private String albumTitle;
    private String artist;
    private String since;
    private String image;

}
