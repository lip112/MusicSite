package com.exam.mix.service.reply;

import com.exam.mix.dto.board.ReplyDTO;
import com.exam.mix.entity.board.Board;
import com.exam.mix.entity.board.Reply;
import com.exam.mix.repository.reply.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class ReplyServiceImpl implements ReplyService{
    private final ReplyRepository replyRepository;
    private final EntityManager entityManager;

    @Override
    public ReplyDTO register(ReplyDTO replyDTO) {
        Reply reply = replyRepository.save(DtoToEntity(replyDTO));
        if (reply.getRno() != null) {
            return EntityToDto(reply);
        } else {
            return null;
        }
    }

    @Override // 댓글을 작성한 사용자인지 파악하는 메서드
    public void checkDuplicateNickname(String nickname, Long rno) {
        replyRepository.findByReplyerAndRno(nickname, rno);
    }

    @Override// 닉네임이 동일하니 실행
    public void modifyReply(ReplyDTO replyDTO) {
        //JPA에서는 트랜잭션이 끝나는 시점에 변화가 있는 모든 엔티티 객체를 데이터베이스에 자동으로 반영해 준다. 영속성에 있는 값을찾아 변경
        Reply reply = entityManager.find(Reply.class, replyDTO.getRno());
        reply.changeContent(replyDTO.getContent());
    }

    @Override
    public void deleteReply(ReplyDTO replyDTO) {
        replyRepository.deleteById(replyDTO.getRno());
    }

    @Override
    public List<ReplyDTO> findAll(Long bno) {
        List<Reply> replyList = replyRepository.findByBoard(Board.builder().bno(bno).build());

        //Reply을 형변환해서 Entity로 바꿔서 넣어줌, 실패시 전역예외처리에서 예외처리함.
        return replyList.stream()
                .map(Reply -> EntityToDto(Reply))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAllByBno(Long bno) {//게시글 삭제시 해당 bno 댓글 삭제
            replyRepository.deleteByBoard_Bno(bno);
    }
}
