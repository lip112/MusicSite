package music.musicsite.service.song;

import lombok.RequiredArgsConstructor;
import music.musicsite.dto.song.SongDto;
import music.musicsite.dto.song.SongProjectionInterface;
import music.musicsite.entity.song.Song;
import music.musicsite.repository.song.SongRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SongService {
    private final SongRepository songRepository;

    public void requestSong(SongDto songDto) {
        songRepository.save(Song.from(songDto));
    }

    public void modifySong(SongDto songDto) {
        Song song = songRepository.findByHakbun(songDto.getHakbun())
                .orElseThrow(() -> new UsernameNotFoundException("신청한 사용자가 아닙니다."));

        song.changeArtist(songDto.getArtist());
        song.changeTitle(songDto.getTitle());
    }

    public SongDto getSong(String hakbun) {
        Song song = songRepository.findByHakbun(hakbun)
                .orElseThrow(() -> new NullPointerException("신청한 노래가 없습니다."));

        return SongDto.from(song);
    }

    public List<SongDto> getRanKing(LocalDateTime start, LocalDateTime end) {
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
