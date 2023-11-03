package music.musicsite.config.Security;

import music.musicsite.entity.user.User;
import music.musicsite.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    @Override // 로그인시 실행되고 authentication로 값을 돌려줌.
    public UserDetails loadUserByUsername(String hakbun) throws UsernameNotFoundException {
        System.out.println("loadUserByUsernaeme = " + hakbun);
        User user = userRepository.findByNickname(hakbun)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 사용자 입니다."));
        return UserDetails.builder()
                .hakbun(user.getHakbun())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .role(user.getRole())
                .build();
    }
}
