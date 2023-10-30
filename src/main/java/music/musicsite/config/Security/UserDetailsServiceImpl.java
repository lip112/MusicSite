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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("loadUserByUsernaeme = " + email);
        Optional<User> user = userRepository.findByEmail(email);
        return UserDetails.builder()
                .email(user.get().getEmail())
                .password(user.get().getPassword())
                .nickname(user.get().getNickname())
                .role(user.get().getRole())
                .build();
    }
}
