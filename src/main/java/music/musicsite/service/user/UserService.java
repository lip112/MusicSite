package music.musicsite.service.user;

import lombok.RequiredArgsConstructor;
import music.musicsite.customexception.DuplicateUserException;
import music.musicsite.dto.user.UserDto;
import music.musicsite.entity.user.User;
import music.musicsite.repository.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    public void signup(UserDto userDto) throws Exception {
        System.out.println("userDto = " + userDto);
        if (!isValidUserDto(userDto)) {
            throw new IllegalArgumentException("유효하지 않은 사용자 정보입니다.");
        }
        Optional<User> byHakbun = userRepository.findByHakbun(userDto.getHakbun());
        if (byHakbun.isPresent()) {
            throw new DuplicateUserException("이미 존재하는 아이디입니다.");
        } else {
            User user = User.createUser(userDto.getHakbun(), userDto.getNickname(), userDto.getPassword());
            userRepository.save(user);
        }
    }
    public void login(UserDto userDto) {
        User user = userRepository.findByHakbunAndPassword(userDto.getHakbun(), userDto.getPassword())
                .orElseThrow(() -> new IllegalArgumentException("아이디 혹은 비밀번호가 일치하지 않습니다."));
        // 성공시 create token
    }
    public void logout(UserDto userDto) {
        //로그아웃시 refreshToken delete
    }
    public void changePassword(UserDto userDto, String afterPassword) {
        if (!isValidConfirmKey(userDto.getConfirmKey())) {
            throw new IllegalArgumentException("이메일 인증번호가 맞지 않습니다.");
        }
        User user = userRepository.findByHakbun(userDto.getHakbun())
                .orElseThrow(() -> new NullPointerException("회원이 존재하지 않습니다."));
        user.changePassword(afterPassword);
    }
    public void confirmEmail(UserDto userDto) {
        if (!isValidConfirmKey(userDto.getConfirmKey())) {
            throw new IllegalArgumentException("이메일 인증번호가 맞지 않습니다.");
        }
        //비교했는데 성공하면 뭘 보내줘야할지 모름.
    }

    private boolean isValidUserDto(UserDto userDto){
        if (userDto == null) {
            return false;
        }
        if (userDto.getNickname().isEmpty()){
            return false;
        }
        if (userDto.getPassword().isEmpty()){
            return false;
        }
        if (userDto.getHakbun() <= 0) { // 나중에 학번안넣고 값만 받아 온 다음에 어떤값 들어있는지파악
            return false;
        }
        return true;
    }


    private boolean isValidConfirmKey(String createKey){
        String imsi = "나중에 전역변수 키로 바꿀것";
        return imsi.equals(createKey);
    }
}
