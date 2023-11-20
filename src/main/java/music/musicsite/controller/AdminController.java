package music.musicsite.controller;

import lombok.RequiredArgsConstructor;
import music.musicsite.dto.response.ResponseDto;
import music.musicsite.dto.user.UserDTO;
import music.musicsite.service.admin.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService managerService;
    @GetMapping("/list")
    public ResponseEntity<ResponseDto<List<UserDTO>>> getList() {
        List<UserDTO> list = managerService.getList();
        return ResponseEntity.ok(new ResponseDto<>(list, "리스트를 불러왔습니다."));
    }

    @PutMapping("/modify")
    public ResponseEntity<ResponseDto<String>> modifyUserInfo(@RequestBody UserDTO userDTO) {
        System.out.println("Admin modify userDTO = " + userDTO);
        managerService.modifyUserInfo(userDTO);
        return ResponseEntity.ok(new ResponseDto<>("변경되었습니다."));
    }
}
