package music.musicsite.repository.visitor;

import music.musicsite.entity.visitor.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Long> {

    @Query(value = "select v " +
            " from Visitor as v" +
            " where v.email = :email AND substring(v.regDate, 1, 10) = substring(:date, 1, 10)")//date()는 날짜만 따로 매핑해줌
    Optional<Visitor> findByEmailAndRegDate(@Param("email") String email, @Param("date") LocalDateTime date);

    int countBy();

    @Query(value = " select count(*)" +
            " from Visitor as v " +
            " where substring(v.regDate, 1, 10) = substring(:date, 1, 10) ")
    int countByTodayCount(@Param("date") LocalDateTime date);
}
