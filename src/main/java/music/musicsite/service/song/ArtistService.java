package music.musicsite.service.song;

import lombok.RequiredArgsConstructor;
import music.musicsite.config.selenium.WebDriverUtil;
import music.musicsite.dto.music.ArtistDto;
import music.musicsite.dto.music.SearchResult;
import music.musicsite.dto.music.SongDto;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtistService {
    private final ResourceLoader resourceLoader;
    private WebDriver driver;

    public static final int PREV_BUTTON = 888;
    public static final int NEXT_BUTTON = 999;

    public SearchResult<List<ArtistDto>> searchArtist(String searArtist) throws IOException {
        String url = "https://www.genie.co.kr/search/searchArtist?query=" + searArtist + "&Coll=";

        driver = WebDriverUtil.getChromeDriver(resourceLoader);
        driver.get(url);
        List<ArtistDto> artists = new ArrayList<>();

        List<WebElement> elements = driver.findElements(By.className("artist-item"));
        String totalArtist = driver.findElement(By.xpath("//*[@id=\"body-content\"]/div[4]/div[1]/span/strong")).getText();
        System.out.println("totalArtist = " + totalArtist);


        for (WebElement web : elements) {
            ArtistDto artist = new ArtistDto();

            WebElement imageElement = web.findElement(By.cssSelector("div.dumy > a > img"));
            artist.setImage(imageElement.getAttribute("src"));

            String artistId = web.findElement(By.cssSelector("div.dumy > a")).getAttribute("onclick");
            //slicing -> "fnViewAlbumLayer('80523187');return false;" -> 80523187
            int startIndex = artistId.indexOf("'") + 1; // 시작 따옴표 이후 인덱스
            int endIndex = artistId.lastIndexOf("'"); // 마지막 따옴표 인덱스
            String result = artistId.substring(startIndex, endIndex);
            artist.setArtistId(result);

            WebElement infoElement = web.findElement(By.cssSelector("div.dumy > p.cont"));
            String findArtist = infoElement.findElement(By.className("artist")).getText();
            artist.setArtist(findArtist);

            String country = infoElement.findElement(By.className("nationality")).getText();
            artist.setCountry(country);

            String gender = infoElement.findElement(By.className("type")).getText();
            artist.setGender(gender);

            artists.add(artist);

            System.out.println("album = " + artist);
        }
        System.out.println("artists.size() = " + artists.size());
        driver.quit();
        return new SearchResult<>(totalArtist, artists);
    }
    //다른 검색들은 검색 이후에 연계 작업이 없어서 종료 시켰지만 artistDetailSong, movePage는 따로 url을 통해 작업이 불가능, 따로 종료하는 메서드를 만들어서 조요함
    public SearchResult<List<SongDto>> artistDetailSong(String ArtistId) throws IOException {

        String url = "https://www.genie.co.kr/detail/artistSong?xxnm=" + ArtistId;

        driver = WebDriverUtil.getChromeDriver(resourceLoader);
        driver.get(url);
        List<SongDto> songs = new ArrayList<>();

        String totalSongCount = driver.findElement(By.cssSelector("#songlist-box > div.tit-box > span > strong")).getText();
        System.out.println("totalSongCount = " + totalSongCount);

        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.className("list-wrap")));


        List<WebElement> elements = element.findElements(By.cssSelector("tr.list"));//tr태그의 list클래스

        for (WebElement web : elements) {
            SongDto song = new SongDto();

            WebElement imageElement = web.findElement(By.cssSelector("a.cover > img"));
            song.setImage(imageElement.getAttribute("src"));

            WebElement infoElement = web.findElement(By.className("info"));
            String title = infoElement.findElement(By.cssSelector("a.title.ellipsis")).getAttribute("title");
            song.setTitle(title);

            String artist = infoElement.findElement(By.cssSelector("a.artist.ellipsis")).getText();
            song.setArtist(artist);

            String album = infoElement.findElement(By.cssSelector("a.albumtitle.ellipsis")).getText();
            song.setAlbum(album);

            songs.add(song);

            System.out.println("song = " + song);
        }
        System.out.println("songs.size() = " + songs.size());

        return new SearchResult<>(totalSongCount, songs);
    }


    public SearchResult<List<SongDto>> movePage(int page) throws InterruptedException, IOException {

        driver = WebDriverUtil.getChromeDriver(resourceLoader);
        List<SongDto> songs = new ArrayList<>();

        //page number
        WebElement element1 = driver.findElement(By.className("page-nav"));
        List<WebElement> pageElement = element1.findElements(By.cssSelector("div.page-nav > a"));
        pageElement.remove(pageElement.size() - 1);
        pageElement.remove(0);

        new WebDriverWait(driver, Duration.ofSeconds(100))
                .until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));

        if (page == PREV_BUTTON) {
            pageElement.get(0).click();
        } else if (page == NEXT_BUTTON) {
            pageElement.get(pageElement.size() - 1).click();
        } else {
            pageElement.get(page).click();
        }

        Thread.sleep(1000);

        new WebDriverWait(driver, Duration.ofSeconds(100))
                .until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));

        Thread.sleep(1000);

        WebElement element = driver.findElement(By.className("list-wrap"));

        String totalSongCount = driver.findElement(By.cssSelector("#songlist-box > div.tit-box > span > strong")).getText();
        System.out.println("totalSongCount = " + totalSongCount);

        List<WebElement> elements = element.findElements(By.cssSelector("tr.list"));//tr태그의 list클래스

        for (WebElement web : elements) {
            SongDto song = new SongDto();

            String src = web.findElement(By.cssSelector("a.cover > img")).getAttribute("src");
            song.setImage(src);

            WebElement infoElement = web.findElement(By.className("info"));
            String title = infoElement.findElement(By.cssSelector("a.title.ellipsis")).getAttribute("title");
            song.setTitle(title);

            String artist = infoElement.findElement(By.cssSelector("a.artist.ellipsis")).getText();
            song.setArtist(artist);

            String album = infoElement.findElement(By.cssSelector("a.albumtitle.ellipsis")).getText();
            song.setAlbum(album);

            songs.add(song);

            System.out.println("song = " + song);
        }
        System.out.println("songs.size() = " + songs.size());

        return new SearchResult<>(totalSongCount, songs);
    }

    public void closeDrvier() {
        driver.close();
    }
}
