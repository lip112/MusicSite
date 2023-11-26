package music.musicsite.service.song;

import lombok.RequiredArgsConstructor;
import music.musicsite.dto.song.SongDto;
import music.musicsite.dto.song.SongProjectionInterface;
import music.musicsite.entity.song.Song;
import music.musicsite.repository.song.SongRepository;
import org.springframework.security.core.Transient;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SongService {
    private final SongRepository songRepository;

    public void requestSong(SongDto songDto) {
        songRepository.save(Song.from(songDto));
    }

    public void modifySong(SongDto songDto) {
        //어제 11시1분 이후로 신청한 내역을 가져옴
        LocalDateTime localDateTime = LocalDateTime.now().minusDays(1).withHour(11).withMinute(0);
        Song song = songRepository.findTodaySong(songDto.getNickname(), localDateTime)
                .orElseThrow(() -> new NullPointerException("신청한 노래가 없습니다."));

        song.changeArtist(songDto.getArtist());
        song.changeTitle(songDto.getTitle());
    }

    public SongDto getSong(String nickname) {
        //어제 11시1분 이후로 신청한 내역을 가져옴
        LocalDateTime localDateTime = LocalDateTime.now().minusDays(1).withHour(11).withMinute(0);
        Song song = songRepository.findTodaySong(nickname, localDateTime)
                .orElseThrow(() -> new NullPointerException("신청한 노래가 없습니다."));

        return SongDto.from(song);
    }

    public List<SongDto> getRanKing(LocalDateTime start, LocalDateTime end) {

        start = start.withHour(0)
                .withMinute(0)
                .withSecond(1);
        List<SongProjectionInterface> ranking = songRepository.findRanking(start, end);
        return ranking
                .stream()
                .map(SongDto::from)
                .collect(Collectors.toList());
    }

    public List<SongDto> getTodaySong() {
        LocalDateTime end = LocalDateTime.now();
        LocalDateTime start = end.minusDays(1);
        List<SongProjectionInterface> ranking = songRepository.findRanking(start, end);
        return ranking
                .stream()
                .limit(10)
                .map(SongDto::from)
                .collect(Collectors.toList());
    }
}
