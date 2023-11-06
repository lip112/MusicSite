package music.musicsite.service.manager;

import lombok.RequiredArgsConstructor;
import music.musicsite.dto.user.UserDTO;
import music.musicsite.entity.user.User;
import music.musicsite.repository.user.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void modifyUserInfo(UserDTO userDTO) {
        User user = userRepository.findByHakbun(userDTO.getHakbun())
                .orElseThrow(() -> new NullPointerException("존재하지 않는 학번 입니다."));

        user.changeRole(userDTO.getRole());
        user.changeNickname(userDTO.getNickname());
        user.changePassword(passwordEncoder.encode(userDTO.getPassword()));
    }

    public List<UserDTO> getList() {
        return userRepository.findAll()
                .stream()
                .map(UserDTO::from)
                .collect(Collectors.toList());
    }
}
