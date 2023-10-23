package music.musicsite.config.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.List;


// 토큰을 생성하고 검증하는 클래스입니다.
// 해당 컴포넌트는 필터클래스에서 사전 검증을 거칩니다.
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    private String accessSecretKey = "RWEPKFSDF3242332!@#vxvcx#$$@gFDGDFGDSFSWASDWDdsaweaea";
    // 토큰 유효시간 1 * 60 * 1000L 1분
    private long accessTokenValidTime = 30 * 60 * 1000L;

    private String refreshSecretKey = "RWEPKFSDF3242332!@#vxvcx#$$@gFDGDFGDSFSWMeassdsaweaea";
    // 토큰 유효시간 60분
    private long refreshTokenValidTime = 10 * 60 * 60 * 1000L;

    //객체 초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected void init() {
        accessSecretKey = Base64.getEncoder().encodeToString(accessSecretKey.getBytes());
        refreshSecretKey = Base64.getEncoder().encodeToString(refreshSecretKey.getBytes());
    }

    public void createToken(String hakbun, List<String> roles) {

    }
}
