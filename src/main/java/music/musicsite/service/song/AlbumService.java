package music.musicsite.service.song;

import lombok.RequiredArgsConstructor;
import music.musicsite.config.selenium.WebDriverUtil;
import music.musicsite.dto.music.AlbumDto;
import music.musicsite.dto.music.SearchResult;
import music.musicsite.dto.music.SongDto;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlbumService {
    private final ResourceLoader resourceLoader;

    public SearchResult<List<AlbumDto>> searchAlbum(String albumTitle, int page) throws IOException {
        String url = "https://www.genie.co.kr/search/searchAlbum?query=" + albumTitle + "&page=" + page +"&pagesize=20&of=SCORE&fscount=&Genre=&Country=&reQuery=&researchyn=N";

        WebDriver driver = WebDriverUtil.getChromeDriver(resourceLoader);
        driver.get(url);
        List<AlbumDto> albums = new ArrayList<>();


        List<WebElement> elements = driver.findElements(By.className("list-album"));
        String totalSongCount = driver.findElement(By.xpath("//*[@id=\"body-content\"]/div[4]/div[1]/span/strong")).getText();
        for (WebElement web : elements) {
            AlbumDto albumDto = new AlbumDto();

            WebElement imageElement = web.findElement(By.cssSelector("a.album-cover > img"));
            albumDto.setImage(imageElement.getAttribute("src"));

            WebElement infoElement = web.findElement(By.className("album-info"));
            String album_id = infoElement.findElement(By.cssSelector("dt.ellipsis > a")).getAttribute("onclick");
            //slicing -> "fnViewAlbumLayer('80523187');return false;" -> 80523187
            int startIndex = album_id.indexOf("'") + 1; // 시작 따옴표 이후 인덱스
            int endIndex = album_id.lastIndexOf("'"); // 마지막 따옴표 인덱스
            String result = album_id.substring(startIndex, endIndex);
            albumDto.setAlbumId(result);

            String title = infoElement.findElement(By.cssSelector("dt.ellipsis > a")).getText();
            albumDto.setAlbumTitle(title);

            String artist = infoElement.findElement(By.className("ellipsis")).getText();
            albumDto.setArtist(artist);

            String since = infoElement.findElement(By.cssSelector("dd.desc")).getText();
            albumDto.setSince(since);

            albums.add(albumDto);

            System.out.println("album = " + albumDto);
        }
        System.out.println("albums.size() = " + albums.size());

        driver.quit();

        return new SearchResult<>(totalSongCount, albums);
    }

    public SearchResult<List<SongDto>> DetailAlbum(String albumId) throws IOException {
        String url = "https://www.genie.co.kr/detail/albumInfo?axnm=" + albumId;

        WebDriver driver = WebDriverUtil.getChromeDriver(resourceLoader);
        driver.get(url);
        List<SongDto> songs = new ArrayList<>();

        AlbumDto album = new AlbumDto();

        String album_image = driver.findElement(By.cssSelector("#body-content > div.album-detail-infos > div.photo-zone > a > span.cover > img")).getAttribute("src");
        album.setImage(album_image);

        String artist = driver.findElement(By.cssSelector("#body-content > div.album-detail-infos > div.info-zone > ul > li:nth-child(1) > span.value > a")).getText();
        album.setArtist(artist);

        String since = driver.findElement(By.cssSelector("#body-content > div.album-detail-infos > div.info-zone > ul > li:nth-child(5) > span.value")).getText();
        album.setSince(since);

        WebElement element = driver.findElement(By.className("list-wrap"));
        List<WebElement> elements = element.findElements(By.cssSelector("tr.list"));//tr태그의 list클래스

        for (WebElement web : elements) {

            SongDto song = new SongDto();

            WebElement infoElement = web.findElement(By.className("info"));

            String title = infoElement.findElement(By.cssSelector("a.title.ellipsis")).getAttribute("title");
            song.setTitle(title);

            String song_artist = infoElement.findElement(By.cssSelector("a.artist.ellipsis")).getText();
            song.setArtist(song_artist);

            songs.add(song);

            System.out.println("song = " + song);
        }
        System.out.println("albums.size() = " + songs.size());

        driver.quit();
        SearchResult<List<SongDto>> listSearchResult = new SearchResult<>(songs, album);
        listSearchResult.setTotalCount(String.valueOf(songs.size()));
        return listSearchResult;
    }
}
