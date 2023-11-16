package com.exam.mix.repository;


import music.musicsite.dto.page.PageRequestDTO;
import music.musicsite.entity.board.Board;
import music.musicsite.entity.user.User;
import music.musicsite.repository.board.BoardRepository;
import music.musicsite.service.board.BoardService;
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
                    .userId((long) i)
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
//        boardService.getList(pageRequestDTO, B);
    }

    @Test
    public void BoardGetBno(){
        boardService.getBoardId((long)1);
    }
}
