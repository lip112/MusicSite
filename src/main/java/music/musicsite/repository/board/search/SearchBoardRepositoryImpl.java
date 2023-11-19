package music.musicsite.repository.board.search;

import music.musicsite.dto.board.BoardDTO;
import music.musicsite.dto.board.BoardWithReplyDTO;
import music.musicsite.dto.board.ReplyDTO;
import music.musicsite.entity.board.Board;
import music.musicsite.entity.board.QBoard;
import music.musicsite.entity.board.QReply;
import music.musicsite.entity.user.QUser;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository {
    public SearchBoardRepositoryImpl() {
        super(Board.class);
    }

    @Override
    public Page<Tuple> getList(Pageable pageable, BoardDTO boardDTO) {
        log.info("getList.................");

        QBoard board = QBoard.board;
        QReply reply = QReply.reply;
        QUser user = QUser.user;

        //먼저 where을 통해 조건식으로 board를 선별한다.
        //2.왼쪽에 board테이블을 놓고 오른쪽에 user테이블을 배치한다.
        //3.on을 실행하는데 board.writer 즉 board테이블의 witer칼럼(FK)과 member테이블의 PK값을 비교하고 값을 반환
        //4.2개의 왼쪽테이블이 존재하고 reply테이블을 가지고 left join 실행
        //5.reply에서 fk로 참조한 gno = board에 있는 pk와 비교한다.
        //아래 코드는 보기 어렵지만 상세하게 적은것이다.
        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(user).on(board.writer.eq(user));//user의 PK(uid)와 Board의 FK(Uid)를 비교
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board)); //reply의 FK(bno)와 Board의 PK를 비교
        jpqlQuery.where(board.category.eq(boardDTO.getCategory()));
        jpqlQuery.orderBy(board.boardId.desc());

        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, user.nickname, reply.count());
        tuple.groupBy(board);

        log.info("-------------------------");
        log.info(tuple);
        log.info("-------------------------");

        List<Tuple> result = tuple.fetch();


        log.info(result);
        return convert(result, pageable.getPageNumber(), pageable.getPageSize());
    }

    @Override
    public List<BoardWithReplyDTO> getBoardId(long boardId) {
        log.info("getbno.................");

        QBoard board = QBoard.board;
        QReply reply = QReply.reply;

        //위에 코드보다 쉽게 가독성 있게 적은 코드다.
        List<Tuple> fetch = from(board)
                .select(board, reply)
                .leftJoin(reply).on(reply.board.eq(board))
                .where(board.boardId.eq(boardId))
                .fetch();

        //댓글이 없을경우 게시글만 보내주기
        if (fetch.get(0).get(reply) == null) {
            List<BoardWithReplyDTO> list = new ArrayList<BoardWithReplyDTO>();
            list.add(BoardDTO.builder()
                    .boardId(fetch.get(0).get(board).getBoardId())
                    .title(fetch.get(0).get(board).getTitle())
                    .content(fetch.get(0).get(board).getContent())
                    .writer(fetch.get(0).get(board).getWriter().getNickname())
                    .replyCount(fetch.size())
                    .regDate(String.valueOf(fetch.get(0).get(board).getRegDate()))
                    .modDate(String.valueOf(fetch.get(0).get(board).getModDate()))
                    .build()
            );
            return list;
        }

        return TupleToList(fetch);

    }

    //gpt가 해줌 List를 Page로 바꾸기
    private Page<Tuple> convert(List<Tuple> tupleList, int page, int pageSize) {
        int start = page * pageSize;
        int end = Math.min(start + pageSize, tupleList.size());
        List<Tuple> content = tupleList.subList(start, end);

        Pageable pageable = PageRequest.of(page, pageSize);
        return new PageImpl<>(content, pageable, tupleList.size());
    }


    //쿼리 보내고 나온 결과 Tuple<List>를 하나씩 꺼내서 DTO로 변환후 List<Object>로 변환 후 반환
    private List<BoardWithReplyDTO> TupleToList(List<Tuple> fetch) {
        QBoard board = QBoard.board;
        QReply reply = QReply.reply;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


        List<BoardWithReplyDTO> list = new ArrayList<BoardWithReplyDTO>();
        list.add(BoardDTO.builder()
                .boardId(fetch.get(0).get(board).getBoardId())
                .title(fetch.get(0).get(board).getTitle())
                .content(fetch.get(0).get(board).getContent())
                .writer(fetch.get(0).get(board).getWriter().getNickname())
                .replyCount(fetch.size())
                .regDate(fetch.get(0).get(board).getRegDate().format(formatter))
                .modDate(fetch.get(0).get(board).getModDate().format(formatter))
                .build()
        );
        for (int i = 0; i < fetch.size(); i++) {
            list.add(ReplyDTO.builder()
                    .replyId(fetch.get(i).get(reply).getReplyId())
                    .content(fetch.get(i).get(reply).getContent())
                    .replyer(fetch.get(i).get(reply).getReplyer())
                    .writeReplyDate(fetch.get(i).get(reply).getWriteReplyDate())
                    .boardId(fetch.get(i).get(reply).getBoard().getBoardId())
                    .build()
            );
        }
        return list;
    }
}

