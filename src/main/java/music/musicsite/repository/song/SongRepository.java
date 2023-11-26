package music.musicsite.repository.song;

import music.musicsite.dto.song.SongProjectionInterface;
import music.musicsite.entity.song.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SongRepository extends JpaRepository<Song, Long> {

    Optional<Song> findByNickname(String nickname);

    @Query(value = " select s " +
            " from Song s " +
            " where s.nickname = :nickname AND s.regDate >= :time")
    Optional<Song> findTodaySong(@Param("nickname") String nickname,@Param("time") LocalDateTime localDateTime);

    //별칭을 꼭 적어줘야 매칭이 된다.
    @Query(value = "select s.artist as artist, s.title as title, count(*) as requestCount" +
            " from Song s" +
            " where s.regDate between :startDate AND :endDate " +
            " group by s.artist, s.title" +
            " ORDER BY COUNT(*) DESC")
    List<SongProjectionInterface> findRanking(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
