package music.musicsite.config.Security;

import music.musicsite.config.Security.jwt.JwtAuthenticationFilter;
import music.musicsite.config.Security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@EnableWebSecurity //spring security 를 적용한다는 Annotation
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //WebSecurityConfigurerAdapter 을 상속받는 것과 차이점은 return 타입을 통해 끝에 build()를 해서 반환해줘야 한다는 점입니다.
    //WebSecurityConfigurerAdapter는 이제 쓰지않고 filterChain으로 통합됨. @Configuration을 사용해야함
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//세션을 사용하지 않겠다.
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class) // 오른쪽에 등록한 필터전에 커스텀필터링이 수행된다
                .authorizeRequests() //URI에 따른 페이지에 대한 권한을 부여하기 위해 시작하는 메소드 입니다. 아래의 antMatchers 기능을 이용하기 위한 메소드라고 보면 됩니다.
                .antMatchers("/", "/api/register/**", "/api/notify/**", "/api/board/list/**", "/api/auth/**", "/api/visit/**").permitAll()
                .antMatchers("/api/board/**", "/api/reply/**").hasRole("USER")
                .antMatchers("/aaa").hasRole("ADMIN");
        //.anyRequest().authenticated() //authenticated() 그 외 요청 인증필요 , .permitAll()은 그외 모든요청 허용
        return http.build();
    }

    @Bean
    //cors 설정
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://52.78.213.38")); // 프론트엔드 도메인 주소
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowCredentials(true); // allowCredentials를 true로 설정
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.addExposedHeader(HttpHeaders.SET_COOKIE);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() {
        return new ProviderManager(List.of(
                daoAuthenticationProvider()
        ));
    }

    private DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
}