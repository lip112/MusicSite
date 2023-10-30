package com.exam.mix.repository;

import music.musicsite.dto.user.UserDTO;
import music.musicsite.entity.user.Role;
import music.musicsite.entity.user.User;
import music.musicsite.repository.user.UserRepository;
import music.musicsite.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

@SpringBootTest
public class UserRepositoryTests {
}
