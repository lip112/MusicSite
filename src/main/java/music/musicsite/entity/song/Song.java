package music.musicsite.entity.song;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import music.musicsite.dto.song.SongDto;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(value = { AuditingEntityListener.class})
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long songId;

    private String title;

    private String artist;

    private String nickname;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regDate;

    public static Song from(SongDto songDto) {
        return Song.builder()
                .title(songDto.getTitle())
                .artist(songDto.getArtist())
                .nickname(songDto.getNickname())
                .build();
    }

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeArtist(String artist) {
        this.artist = artist;
    }

    public void changeRegDate(LocalDateTime regDate) {
        this.regDate = regDate;
    }
}
