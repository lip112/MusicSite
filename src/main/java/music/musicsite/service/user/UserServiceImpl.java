package music.musicsite.service.user;

import music.musicsite.customException.DuplicateUserException;
import music.musicsite.customException.EmailConfirmCodeNotMatchingException;
import music.musicsite.dto.user.UserDTO;
import music.musicsite.entity.user.Role;
import music.musicsite.entity.user.User;
import music.musicsite.repository.user.UserRepository;
import music.musicsite.service.mail.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder PasswordEncoder;


    @Override
    public void signUp(UserDTO userDTO) throws Exception {
        log.info("signup..." + userDTO);

        if (!userDTO.getCreateKey().equals(EmailService.EMAIL_CONFIRM_CODE)) {
            throw new EmailConfirmCodeNotMatchingException("이메일 인증번호가 일치하지 않습니다.");
        }

        userDTO.setPassword(PasswordEncoder.encode(userDTO.getPassword()));
        userDTO.setRole(Role.ROLE_USER);

        try {
            User save = userRepository.save(User.from(userDTO)); // 이렇게 하면 저장이 되고 난 후에 DB에 저장된 데이터가 저장됨.
            UserDTO.from(save);

        } catch (Exception e) {
            throw new DuplicateUserException("이미 존재하는 이메일 입니다.");
        }
    }
    //로그인 중복 시에 예외처리는 아직 동작 확인 안함.

    @Override
    public UserDTO login(final UserDTO userDTO) {
        log.info("login..." + userDTO);


        Optional<User> user = userRepository.findByEmailAndPassword(userDTO.geth(), userDTO.getPassword());


        return EntityToDto(user.get());
    }

    @Override
    public UserDTO logout(final UserDTO userDTO) { // 시큐리티 만들면 완성할예정.
        log.info("logout..." + userDTO);
        return null;
    }

    @Override
    public void updatePassword(UserDTO userDTO) { //이건 추후에 웹메일 인증으로 바꿀예정
        log.info("updatePassword...");
        System.out.println("EmailServiceImpl.ePw = " + EmailService.EMAIL_CONFIRM_CODE);
        //이메일 인증 한번 더
        if (userDTO.getCreateKey().equals(EmailService.EMAIL_CONFIRM_CODE)) {
            userDTO.setPassword(PasswordEncoder.encode(userDTO.getPassword()));//비밀번호 암호화
            userRepository.updatePassword(userDTO.getEmail(), userDTO.getPassword());
        } else {
            throw new IllegalArgumentException("이메일 인증이 맞지 않습니다");
        }
    }

    @Override
    public boolean checkDuplicateNickname(final String nickname) {
        log.info("signup..." + nickname);
        Optional<User> byNickname = userRepository.findByNickname(nickname);
        if (byNickname.isEmpty()){
            return true;
        }
        //닉네임을 찾아서 중복되면 이미 존재하기 때문에 false 보내 실패를 알린다.
        else if (byNickname.get().getNickname().equals(nickname)) {
            return false;
        } else {
            return true;
        }
    }

}
