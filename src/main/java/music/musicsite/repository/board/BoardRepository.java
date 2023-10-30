package music.musicsite.repository.board;

import music.musicsite.entity.board.Board;
import music.musicsite.repository.board.search.SearchBoardRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>, SearchBoardRepository {
    @Query(value = "select b " +
            " from Board b " +
            " where b.writer.nickname = :nickname AND b.bno = :bno ")
    Board findByWriterwithBno(@Param("nickname") String nickname, @Param("bno") Long bno);

}
