package music.musicsite.repository.song;

import music.musicsite.dto.song.SongProjectionInterface;
import music.musicsite.entity.song.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SongRepository extends JpaRepository<Song, Long> {

    Optional<Song> findByHakbun(String hakbun);

    //별칭을 꼭 적어줘야 매칭이 된다.
    @Query(value = "select s.artist as artist, s.title as title, count(*) as requestCount" +
            " from Song s" +
            " where substring(s.regDate, 1, 10) between substring(:startDate, 1, 10) AND substring(:endDate, 1, 10)  " +
            " group by s.artist, s.title" +
            " ORDER BY COUNT(*) DESC")
    List<SongProjectionInterface> findRanking(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
