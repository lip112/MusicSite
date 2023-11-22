package music.musicsite.dto.song;


import lombok.*;
import music.musicsite.entity.song.Song;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SongDto {
    private String title;

    private String artist;

    private String album;

    private String image;

    private LocalDateTime regDate;

    private String nickname;

    private Long count;

    private Long songId;

    public static SongDto from(Song song) {
        return SongDto.builder()
                .title(song.getTitle())
                .artist(song.getArtist())
                .regDate(song.getRegDate())
                .build();
    }

    public static SongDto from(SongProjectionInterface song) {
        SongDto songDto = new SongDto();
        songDto.setTitle(song.getTitle());
        songDto.setArtist(song.getArtist());
        songDto.setCount(song.getRequestCount());
        return songDto;
    }
}
