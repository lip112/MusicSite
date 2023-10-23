package com.exam.mix.repository;


import com.exam.mix.dto.page.PageRequestDTO;
import com.exam.mix.entity.board.Board;
import com.exam.mix.entity.user.User;
import com.exam.mix.repository.board.BoardRepository;
import com.exam.mix.service.board.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private BoardService boardService;

    @Test
    public void BoardAddData(){
        IntStream.rangeClosed(1, 101).forEach(i ->{
            User user = User.builder()
                    .uid((long) i)
                    .build();
            Board board = Board.builder()
                    .title("title..." + i)
                    .content("content..." + i)
                    .writer(user)
                    .build();
            boardRepository.save(board);
        });
    }
    @Test
    public void BoardList() {
        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        boardService.getList(pageRequestDTO);
    }

    @Test
    public void BoardGetBno(){
        boardService.getBno((long)1);
    }
}
