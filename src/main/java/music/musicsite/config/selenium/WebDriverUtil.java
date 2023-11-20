package music.musicsite.config.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;

@Component
public class WebDriverUtil {

    public static WebDriver getChromeDriver(ResourceLoader resourceLoader) throws IOException {
        if (ObjectUtils.isEmpty(System.getProperty("webdriver.chrome.driver"))) {
            // WebDriver 경로

            try{
                Resource resource = resourceLoader.getResource("classpath:chromedriver");
                File file = resource.getFile();
                String absolutePath = file.getAbsolutePath();
                System.setProperty("webdriver.chrome.driver", absolutePath);
            } catch (FileNotFoundException e){
                System.out.println("크롬드라이버를 찾지 못 했습니다.");
                String chromedriverPath = "/home/ec2-user/MusicSite/build/resources/main/chromedriver";
                File chromedriverFile = new File(chromedriverPath);

                System.setProperty("webdriver.chrome.driver", chromedriverFile.getAbsolutePath());

            }
        }

        // webDriver 옵션 설정
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        chromeOptions.addArguments("--lang=ko");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        chromeOptions.addArguments("--disable-gpu"); //gpu를사용하지 않음 리눅스로 헤드리스를 사용할때 필요
        chromeOptions.addArguments("--disable-popup-blocking");       //팝업안띄움
        chromeOptions.addArguments("--blink-settings=imagesEnabled=false"); //이미지 다운 안받음


        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30)); // 접속시 최대 30초기다림

        return driver;
    }

    public static void quit(WebDriver driver) {
        if (!ObjectUtils.isEmpty(driver)) {
            driver.quit();
        }
    }

    public static void close(WebDriver driver) {
        if (!ObjectUtils.isEmpty(driver)) {
            driver.close();
        }
    }

}
