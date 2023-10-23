package com.exam.mix.repository;

import com.exam.mix.dto.user.UserDTO;
import com.exam.mix.entity.user.Role;
import com.exam.mix.entity.user.User;
import com.exam.mix.repository.user.UserRepository;
import com.exam.mix.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

@SpringBootTest
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    @Transactional
    public void UserTest() {
        IntStream.rangeClosed(1, 100).forEach(i ->{
            User user = User.builder()
                    .email(i + "...@naver.com")
                    .password(bCryptPasswordEncoder.encode("1" + i)) //11, 12, 13
                    .nickname("강낭콩" + i)
                    .role(Role.ROLE_USER)
                    .build();
            System.out.println("user = " + user);
            User save = userRepository.save(user);
            System.out.println("save = " + save);
        });
    }

    @Test
    public void LoginTest() throws Exception {
        UserDTO userDTO = UserDTO.builder()
                .email("1... @naver.com")
                .password("11")
                .build();
        System.out.println(userService.login(userDTO));
    }
}
