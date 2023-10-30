package music.musicsite.service.user;

import music.musicsite.dto.user.UserDTO;

import java.util.Map;


public interface UserService {
    void signUp(UserDTO userDTO) throws Exception;
    UserDTO login(UserDTO userDTO) ;

    void updatePassword(UserDTO userDTO);
    boolean  checkDuplicateNickname(String nickname);

}
