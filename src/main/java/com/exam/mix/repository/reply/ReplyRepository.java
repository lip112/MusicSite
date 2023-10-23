package com.exam.mix.repository.reply;

import com.exam.mix.entity.board.Board;
import com.exam.mix.entity.board.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Optional<Reply> findByReplyerAndRno(String replyer, Long rno);
    List<Reply> findByBoard(Board board);

    void deleteByBoard_Bno(Long bno);
}
