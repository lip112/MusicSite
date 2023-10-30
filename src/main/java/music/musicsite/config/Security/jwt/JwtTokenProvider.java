package music.musicsite.config.Security.jwt;

import music.musicsite.dto.token.TokenDTO;
import music.musicsite.entity.user.Role;
import music.musicsite.service.refreshtoken.RefreshTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;

// 토큰을 생성하고 검증하는 클래스입니다.
// 해당 컴포넌트는 필터클래스에서 사전 검증을 거칩니다.
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    private final UserDetailsService userDetailsService;
    private final RefreshTokenService refreshTokenService;

    private String accessSecretKey = "RWEPKFSDF3242332!@#vxvcx#$$@gFDGDFGDSFSWASDWDdsaweaea";
    // 토큰 유효시간 1 * 60 * 1000L 1분
    private long accessTokenValidTime = 30 * 60 * 1000L;

    private String refreshSecretKey = "RWEPKFSDF3242332!@#vxvcx#$$@gFDGDFGDSFSWMeassdsaweaea";
    // 토큰 유효시간 60분
    private long refreshTokenValidTime = 10 * 60 * 60 * 1000L;


    // 객체 초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected void init() {
        accessSecretKey = Base64.getEncoder().encodeToString(accessSecretKey.getBytes());
        refreshSecretKey = Base64.getEncoder().encodeToString(refreshSecretKey.getBytes());
    }

    // JWT 토큰 생성
    public TokenDTO createToken(String userEmail, Role role) {
        Claims claims = Jwts.claims().setSubject(userEmail); // JWT payload 에 저장되는 정보단위, 보통 여기서 user를 식별하는 값을 넣는다.
        claims.put("role", role); // 정보는 key / value 쌍으로 저장된다.
        Date now = new Date();

        //Access Token
        String accessToken = Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + accessTokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, accessSecretKey)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();

        //Refresh Token
        String refreshToken =  Jwts.builder()
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, refreshSecretKey)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();
        //db에 토큰 저장
        refreshTokenService.saveRefreshToken(refreshToken, userEmail, role);


        return TokenDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .key(userEmail)
                .build();
    }

    // JWT 토큰을 복호화 해서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(accessSecretKey).parseClaimsJws(token).getBody().getSubject();
    }



    // 토큰의 유효성 + 만료일자 확인
    public boolean validateAccessToken(String jwtToken) {
        try {
            //JWT를 복호화 해서 유효성을 파악, Jwts: JWT를 처리하는 유틸리티 클래스입니다.
            Jws<Claims> claims = Jwts.parser().setSigningKey(accessSecretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    //리프래쉬 전용 유효성 확인. DB를 확인해야 하기 때문에 메서드를 새로 만듬
    public boolean validateRefreshToken(String refreshToken){
        try {
            // 검증
            Jws<Claims> claims = Jwts.parser().setSigningKey(refreshSecretKey).parseClaimsJws(refreshToken);

            //refresh 토큰의 만료시간이 지나지 않았을 경우, 새로운 access 토큰을 생성합니다.
            if (!claims.getBody().getExpiration().before(new Date())) {
                return true;
            }
        }catch (Exception e) {
            //refresh 토큰이 만료되었을 경우, 로그인이 필요합니다.
            return false;
        }
        return false;
    }

    //httpOnly에 있는 리프래쉬 토큰 가져오기
    public String getRefreshToken(HttpServletRequest request) {
        String refreshToken = "";
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            // 스트림을 사용하여 "Set-Cookie" 이름을 가진 쿠키 찾기
            Cookie setCookie = Arrays.stream(cookies)
                    .filter(cookie -> "refreshToken".equals(cookie.getName()))
                    .findFirst() // 첫 번째로 찾은 쿠키 반환
                    .orElse(null); // 찾지 못하면 null 반환

            return refreshToken = setCookie.getValue();
        }
        else {
            throw new NullPointerException("리프래쉬 토큰이 없습니다.");
        }
    }

    // Request의 Header에서 token 값을 가져옵니다. "Authorization" : "TOKEN값'
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    //유효성 검증을 통과한 후 실행하는 메서드, 새로운 Access Token을 생성하고 반환한다.
    public String recreationAccessToken(String refreshTotken){

        TokenDTO userInfomation = refreshTokenService.findUserInfomation(refreshTotken);

        String userEmail = userInfomation.getEmail();
        Object roles = userInfomation.getRole();

        Claims claims = Jwts.claims().setSubject(userEmail); // JWT payload 에 저장되는 정보단위
        claims.put("roles", roles); // 정보는 key / value 쌍으로 저장된다.
        Date now = new Date();

        //Access Token
        String accessToken = Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + accessTokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, accessSecretKey)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();

        return accessToken;
    }
}
