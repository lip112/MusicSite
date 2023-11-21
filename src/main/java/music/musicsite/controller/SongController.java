package music.musicsite.controller;

import lombok.RequiredArgsConstructor;
import music.musicsite.dto.song.AlbumDto;
import music.musicsite.dto.song.ArtistDto;
import music.musicsite.dto.song.SearchResult;
import music.musicsite.dto.song.SongDto;
import music.musicsite.dto.response.ResponseDto;
import music.musicsite.service.song.AlbumService;
import music.musicsite.service.song.ArtistService;
import music.musicsite.service.song.SongService;
import music.musicsite.service.song.TitleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/song")
public class SongController {
    private final TitleService titleService;
    private final AlbumService albumService;
    private final ArtistService artistService;
    private final SongService songService;

    @GetMapping("/title/{title}/{page}")
    public ResponseEntity<ResponseDto<SearchResult<List<SongDto>>>> searchTitle(@PathVariable("title")String title, @PathVariable("page")int page) throws IOException {
        SearchResult<List<SongDto>> listSearchResult = titleService.searchTitle(title, page);
        return ResponseEntity.ok(new ResponseDto<>(listSearchResult, "제목 검색 결과입니다."));
    }

    @GetMapping("/album/{albumTitle}/{page}")
    public ResponseEntity<ResponseDto<SearchResult<List<AlbumDto>>>> searchAlbum(@PathVariable("albumTitle")String albumTitle, @PathVariable("page")int page) throws IOException {
        SearchResult<List<AlbumDto>> listSearchResult = albumService.searchAlbum(albumTitle, page);
        return ResponseEntity.ok(new ResponseDto<>(listSearchResult, "앨범 검색 결과입니다."));
    }

    @GetMapping("/detail-album/{albumId}")
    public ResponseEntity<ResponseDto<SearchResult<List<SongDto>>>> detailAlbum(@PathVariable("albumId")String albumId) throws IOException {
        SearchResult<List<SongDto>> listSearchResult = albumService.DetailAlbum(albumId);
        return ResponseEntity.ok(new ResponseDto<>(listSearchResult, "앨범 수록곡 결과입니다."));
    }

    @GetMapping("/artist/{artist}")
    public ResponseEntity<ResponseDto<SearchResult<List<ArtistDto>>>> searchArtist(@PathVariable("artist")String artist) throws IOException {
        SearchResult<List<ArtistDto>> listSearchResult = artistService.searchArtist(artist);
        return ResponseEntity.ok(new ResponseDto<>(listSearchResult, "가수 검색 결과입니다."));
    }

    @GetMapping("/artist-song/{artistId}")
    public ResponseEntity<ResponseDto<SearchResult<List<SongDto>>>> detailArtist(@PathVariable("artistId")String artistId) throws IOException {
        SearchResult<List<SongDto>> listSearchResult = artistService.artistDetailSong(artistId);
        return ResponseEntity.ok(new ResponseDto<>(listSearchResult, "해당 가수의 발매곡입니다."));
    }

    @GetMapping("/artist-song/{artist}/{page}")
    public ResponseEntity<ResponseDto<SearchResult<List<SongDto>>>> moveArtistDetailSongPage(@PathVariable("page")int page) throws IOException, InterruptedException {
        SearchResult<List<SongDto>> listSearchResult = artistService.movePage(page);
        return ResponseEntity.ok(new ResponseDto<>(listSearchResult, "해당 가수의 " + page + "page 발매곡입니다."));
    }

    @GetMapping("/artist-song/{artist}/prev")
    public ResponseEntity<ResponseDto<SearchResult<List<SongDto>>>> moveArtistDetailSongPrev() throws IOException, InterruptedException {
        SearchResult<List<SongDto>> listSearchResult = artistService.movePage(ArtistService.PREV_BUTTON);
        return ResponseEntity.ok(new ResponseDto<>(listSearchResult, "해당 가수의 이전 페이지 발매곡입니다."));
    }

    @GetMapping("/artist-song/{artist}/next")
    public ResponseEntity<ResponseDto<SearchResult<List<SongDto>>>> moveArtistDetailSongNext() throws IOException, InterruptedException {
        SearchResult<List<SongDto>> listSearchResult = artistService.movePage(ArtistService.NEXT_BUTTON);
        return ResponseEntity.ok(new ResponseDto<>(listSearchResult, "해당 가수의 다음 페이지 발매곡입니다."));
    }

    @GetMapping("/artist-song/close")
    public ResponseEntity<ResponseDto<String>> closeArtistDriver() {
        artistService.closeDrvier();
        return ResponseEntity.ok(new ResponseDto<>( "성공적으로 종료되었습니다."));
    }

    @PostMapping("/request")
    public ResponseEntity<ResponseDto<String>> requestSong(@RequestBody SongDto songDto) {
        songService.requestSong(songDto);
        return ResponseEntity.ok(new ResponseDto<>( "성공적으로 신청했습니다."));
    }

    @PutMapping("/modify")
    public ResponseEntity<ResponseDto<String>> modifySong(@RequestBody SongDto songDto)  {
        songService.modifySong(songDto);
        return ResponseEntity.ok(new ResponseDto<>("노래 수정을 완료 했습니다."));
    }

    @GetMapping("/request/{nickname}")
    public ResponseEntity<ResponseDto<SongDto>> getRequestSong(@PathVariable("nickname")String nickname) {
        SongDto song = songService.getSong(nickname);
        return ResponseEntity.ok(new ResponseDto<>(song, "신청 내용을 성공적으로 불러왔습니다."));
    }

    @GetMapping("/ranking")
    public ResponseEntity<ResponseDto<List<SongDto>>> getRanking(String start, String end) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDateTime = now
                .withYear(Integer.parseInt(start.substring(0, 4)))
                .withMonth(Integer.parseInt(start.substring(5, 7)))
                .withDayOfMonth(Integer.parseInt(start.substring(8)));
        LocalDateTime endDateTime = now
                .withYear(Integer.parseInt(end.substring(0, 4)))
                .withMonth(Integer.parseInt(end.substring(5, 7)))
                .withDayOfMonth(Integer.parseInt(end.substring(8)));

        List<SongDto> ranKing = songService.getRanKing(startDateTime, endDateTime);
        return ResponseEntity.ok(new ResponseDto<>(ranKing, start + " 부터" + end + " 까지의 노래 순위 입니다."));
    }

    @GetMapping("/today-song")
    public ResponseEntity<ResponseDto<List<SongDto>>> gegTodaySong() {
        List<SongDto> todaySong = songService.getTodaySong();
        return ResponseEntity.ok(new ResponseDto<>(todaySong, "금일 신청노래 top10을 성공적으로 불러왔습니다."));
    }

}
