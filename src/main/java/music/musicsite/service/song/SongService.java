package music.musicsite.service.song;

import lombok.RequiredArgsConstructor;
import music.musicsite.dto.music.SongDto;
import music.musicsite.entity.song.Song;
import music.musicsite.repository.song.SongRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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

}
