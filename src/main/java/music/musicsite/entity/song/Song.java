package music.musicsite.entity.song;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import music.musicsite.dto.music.SongDto;
import music.musicsite.entity.TimeTable;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@Builder
@EntityListeners(value = { AuditingEntityListener.class})
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long songId;

    private String title;

    private String artist;

    private String hakbun;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regDate;

    public static Song from(SongDto songDto) {
        return Song.builder()
                .title(songDto.getTitle())
                .artist(songDto.getArtist())
                .hakbun(songDto.getHakbun())
                .build();
    }

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeArtist(String artist) {
        this.artist = artist;
    }

    public void changeHakbun(String hakbun) {
        this.hakbun = hakbun;
    }
}
