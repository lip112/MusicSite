package music.musicsite.repository.board.search;

import music.musicsite.dto.board.BoardWithReplyDTO;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SearchBoardRepository {
    Page<Tuple> getList(Pageable pageable);

    List<BoardWithReplyDTO> getBoardId(long boardId);

}
