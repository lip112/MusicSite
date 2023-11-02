package music.musicsite.controller;

import music.musicsite.config.Security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import music.musicsite.dto.response.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class TokenController {
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/refreshToken")
    public ResponseEntity<ResponseDto<Map<String, String>>> refreshToken(HttpServletRequest request) {
        System.out.println("refreshToken 재발급");

        //httpOnly에 있는 refreshToken을 가져옴.
        String refreshToken = jwtTokenProvider.getRefreshToken(request);

        System.out.println("refreshToken = " + refreshToken);

        //리프래쉬 토큰이 유효하면 엑세스 토큰만 재발행 한다.
        if (jwtTokenProvider.validateRefreshToken(refreshToken)) {
            System.out.println("유효");
            String newAccessToken = jwtTokenProvider.recreationAccessToken(refreshToken);
            Map<String, String> response = new HashMap<>();
            response.put("accessToken", newAccessToken);
            return ResponseEntity.ok(new ResponseDto<>(response, "RefreshToken이 재발급을 성공했습니다."));
        } else {
            System.out.println("유효하지 않음");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDto<>("RefreshToken이 재발급을 실패했습니다."));
        }
    }
}
