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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    @Override
    public void signUp(UserDTO userDTO) throws Exception {
        log.info("signup..." + userDTO);

        if (!userDTO.getEmailConfirmKey().equals(EmailService.EMAIL_CONFIRM_CODE)) {
            throw new EmailConfirmCodeNotMatchingException("이메일 인증번호가 일치하지 않습니다.");
        }
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userDTO.setRole(Role.ROLE_USER);

        try {
            User save = userRepository.save(User.from(userDTO)); // 이렇게 하면 저장이 되고 난 후에 DB에 저장된 데이터가 저장됨.
            UserDTO.from(save);

        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new DuplicateUserException("이미 존재하는 이메일 입니다.");
        }
    }

    @Override
    public UserDTO login(final UserDTO userDTO) {
        log.info("login..." + userDTO);

        final User user = userRepository.findByHakbun(userDTO.getHakbun())
                .orElseThrow(() -> new NullPointerException("해당 학번의 사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return UserDTO.from(user);
    }

    @Override
    public void updatePassword(UserDTO userDTO) {
        log.info("updatePassword...");
        System.out.println("EmailService.EMAIL_CONFIRM_CODE = " + EmailService.EMAIL_CONFIRM_CODE);
        if (!userDTO.getEmailConfirmKey().equals(EmailService.EMAIL_CONFIRM_CODE)) {
            throw new IllegalArgumentException("이메일 인증번호가 일치하지 않습니다.");
        }
        User user = userRepository.findByHakbun(userDTO.getHakbun())
                .orElseThrow(() -> new NullPointerException("존재 하지 않는 사용자 입니다."));

        user.changePassword(passwordEncoder.encode(userDTO.getPassword()));
    }

    @Override
    public boolean checkDuplicateNickname(final String nickname) {
        log.info("signup..." + nickname);
        Optional<User> byNickname = userRepository.findByNickname(nickname);
        //닉네임이 중복되지 않으면 true
        return byNickname.isEmpty();
    }

}
