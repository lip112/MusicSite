package music.musicsite.controller;

import music.musicsite.config.Security.UserDetails;
import music.musicsite.config.Security.jwt.JwtTokenProvider;
import music.musicsite.dto.response.ResponseDto;
import music.musicsite.dto.token.TokenDTO;
import music.musicsite.dto.user.UserDTO;
import music.musicsite.service.mail.EmailService;
import music.musicsite.service.refreshtoken.RefreshTokenService;
import music.musicsite.service.visitor.VisitorService;
import music.musicsite.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Log4j2
public class UserController {
    private final UserService userService;
    private final EmailService emailService;
    private final VisitorService visitorService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserDTO userDTO, HttpServletResponse response) {
        log.info("login... : " + userDTO);
        Map<String, String> responseData = new HashMap<>();

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userDTO.getHakbun(),
                            userDTO.getPassword()
                    )
            );
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // 인증이 성공하면 JWT 토큰 생성
            TokenDTO token = jwtTokenProvider.createToken(userDetails.getUsername(), userDetails.getAuthorities().stream().map(Object::toString).collect(Collectors.toList()));
            visitorService.incrementVisitorCount(userDTO.getNickname());

            responseData.put("nickname", userDetails.getNickname());
            responseData.put("accessToken", token.getAccessToken());

            Cookie cookie = new Cookie("refreshToken", token.getRefreshToken());
            cookie.setMaxAge(7 * 24 * 60 * 60); // 7일 유효 기간 설정
            cookie.setPath("/"); // 쿠키의 유효 범위를 사이트 전체로 설정
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            response.addCookie(cookie);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(responseData);
        } catch (Exception e) {
            responseData.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(responseData);
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response){
        String refreshToken = jwtTokenProvider.getRefreshToken(request);

        log.info("logout... refreshToken : " + refreshToken);
        refreshTokenService.deleteToken(refreshToken);

        // 쿠키를 초기화 하기위해 생성후 초기화 해준다.
        Cookie refreshTokenCookie = new Cookie("refreshToken", null);

        // 쿠키 만료 시간을 0으로 설정하여 즉시 만료시킵니다.
        refreshTokenCookie.setMaxAge(0);

        // 쿠키가 적용될 경로를 설정합니다.
        refreshTokenCookie.setPath("/");

        // 쿠키를 응답에 추가해서 리프래쉬 토큰을 없앤다.
        response.addCookie(refreshTokenCookie);
        SecurityContextHolder.clearContext(); // 사용자 인증 정보 제거

        return ResponseEntity.ok()
                .build();
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<String>> signup(@RequestBody UserDTO userDTO) throws Exception {
        log.info("signup..." + userDTO);

        userService.signUp(userDTO);

        return ResponseEntity.ok(new ResponseDto<>("회원가입에 성공하셨습니다."));
    }

    @GetMapping("/signup/{nickname}")
    public ResponseEntity<String> checkNickname(@PathVariable("nickname") final String nickname) {
        log.info("checkNickname..." + nickname);

        userService.checkDuplicateNickname(nickname);
        return ResponseEntity.ok()
                .body(nickname);
    }


    @PostMapping("/passwordChange") // 추후에 웹메일 인증할때 바꿔야함.
    public ResponseEntity<Object> passwordChange(@RequestBody UserDTO userDTO) {
        log.info("passwordChange..." + userDTO);

        userService.updatePassword(userDTO);
        return ResponseEntity.ok()
                .build();
    }

    //이메일 인증
    @GetMapping("/emailConfirm/{email}")
    public ResponseEntity<String> emailConfirm(@PathVariable("email") String email) throws Exception {
        log.info("emailConfirm..." + email);

        String confirm = emailService.sendSimpleMessage(email);

        return ResponseEntity.ok()
                .body(confirm);
    }

}
