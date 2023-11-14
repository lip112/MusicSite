package music.musicsite.dto.music;


import lombok.*;
import music.musicsite.entity.song.Song;

import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SongDto {
    private String title;

    private String artist;

    private String album;

    private String image;

    private LocalDateTime regDate;

    private String hakbun;

    public static SongDto from(Song song) {
        return SongDto.builder()
                .title(song.getTitle())
                .artist(song.getArtist())
                .regDate(song.getRegDate())
                .hakbun(song.getHakbun())
                .build();
    }
}
