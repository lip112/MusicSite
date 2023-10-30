package com.exam.mix;

import music.musicsite.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class MixApplicationTests {
    @Autowired
    public UserRepository UserRepository;
    @Test
    void 로그인체크() {

    }

}
