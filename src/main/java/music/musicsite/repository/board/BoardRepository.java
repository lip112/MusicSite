package music.musicsite.repository.board;

import music.musicsite.entity.board.Board;
import music.musicsite.repository.board.search.SearchBoardRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>, SearchBoardRepository {
    @Query(value = "select b " +
            " from Board b " +
            " where b.writer.nickname = :nickname AND b.boardId = :boardId ")
    Optional<Board> findByWriterAndBoardId(@Param("nickname") String nickname, @Param("boardId") Long boardId);


    @Query(value = " select b" +
            " from Board b " +
            "where b.writer.nickname = :nickname")
    Optional<Board> findByWriter(@Param("nickname") String nickname);
}
