package music.musicsite.controller;

import lombok.RequiredArgsConstructor;
import music.musicsite.dto.music.AlbumDto;
import music.musicsite.dto.music.ArtistDto;
import music.musicsite.dto.music.SearchResult;
import music.musicsite.dto.music.SongDto;
import music.musicsite.dto.response.ResponseDto;
import music.musicsite.service.song.AlbumService;
import music.musicsite.service.song.ArtistService;
import music.musicsite.service.song.SongService;
import music.musicsite.service.song.TitleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
        return ResponseEntity.ok(new ResponseDto<>(listSearchResult, "앨범 검색 결과입니다."));
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
    public ResponseEntity<ResponseDto<String>> closeArtistDriver() throws IOException, InterruptedException {
        artistService.closeDrvier();
        return ResponseEntity.ok(new ResponseDto<>( "성공적으로 종료되었습니다."));
    }

    @PostMapping("/request")
    public ResponseEntity<ResponseDto<String>> requestSong(@RequestBody SongDto songDto) throws IOException, InterruptedException {
        songService.requestSong(songDto);
        return ResponseEntity.ok(new ResponseDto<>( "성공적으로 신청했습니다."));
    }

    @PutMapping("/modify")
    public ResponseEntity<ResponseDto<String>> modifySong(@RequestBody SongDto songDto) throws IOException, InterruptedException {
        songService.modifySong(songDto);
        return ResponseEntity.ok(new ResponseDto<>("노래 수정을 완료 했습니다."));
    }



}
