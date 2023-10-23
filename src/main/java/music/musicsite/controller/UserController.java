package music.musicsite.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import music.musicsite.customexception.DuplicateUserException;
import music.musicsite.dto.ResponseDto;
import music.musicsite.dto.user.UserDto;
import music.musicsite.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<String>> signup(@RequestBody UserDto userDto) throws Exception {
        userService.signup(userDto);
        return ResponseEntity.ok(new ResponseDto<>("회원가입에 성공하셨습니다."));
    }

    @GetMapping("/login")
    public ResponseEntity<ResponseDto<String>> login(@RequestBody UserDto userDto) {
        userService.login(userDto);
        //token 로직 수정해야함.
        return ResponseEntity.ok(new ResponseDto<>("Token", "로그인에 성공하셨습니다."));
    }

    @GetMapping("/logout")
    public ResponseEntity<ResponseDto<String>> logout() {
        //리프래쉬토큰 받아서 로그아웃 ㅣㅅ켜야함.
        return ResponseEntity.ok(new ResponseDto<>("Token", "로그아웃에 성공하셨습니다."));
    }
}
