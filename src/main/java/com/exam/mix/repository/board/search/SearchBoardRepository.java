package com.exam.mix.repository.board.search;

import com.exam.mix.dto.board.BoardWithReplyDTO;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SearchBoardRepository {
    Page<Tuple> getList(Pageable pageable);

    List<BoardWithReplyDTO> getbno(long bno);

}
