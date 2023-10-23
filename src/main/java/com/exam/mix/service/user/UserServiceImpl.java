package com.exam.mix.service.user;

import com.exam.mix.config.Security.UserDetails;
import com.exam.mix.config.Security.jwt.JwtTokenProvider;
import com.exam.mix.dto.token.TokenDTO;
import com.exam.mix.dto.user.UserDTO;
import com.exam.mix.entity.user.Role;
import com.exam.mix.entity.user.User;
import com.exam.mix.repository.user.UserRepository;
import com.exam.mix.service.mail.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder PasswordEncoder;



    @Override
    public UserDTO signUp(UserDTO userDTO) {
        log.info("signup..." + userDTO);
        //이메일 인증 한번 더
        if (userDTO.getCreateKey().equals(EmailServiceImpl.ePw)) {
            userDTO.setPassword(PasswordEncoder.encode(userDTO.getPassword()));//비밀번호 암호화
            userDTO.setRole(Role.ROLE_USER);
            try {
                User save = userRepository.save(DtoToEntity(userDTO)); // 이렇게 하면 저장이 되고 난 후에 DB에 저장된 데이터가 저장됨.
                //회원가입 성공시 uid가 null이 아니면 성공
                if (save.getUid() != null) {
                    return EntityToDto(save);
                } else {
                    return null;
                }
            } catch (DataIntegrityViolationException e) {
                throw new DataIntegrityViolationException("이미 존재하는 이메일 입니다.");
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public UserDTO login(final UserDTO userDTO) {
        log.info("login..." + userDTO);


        Optional<User> user = userRepository.findByEmailAndPassword(userDTO.getEmail(), userDTO.getPassword());


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
        System.out.println("EmailServiceImpl.ePw = " + EmailServiceImpl.ePw);
        //이메일 인증 한번 더
        if (userDTO.getCreateKey().equals(EmailServiceImpl.ePw)) {
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
