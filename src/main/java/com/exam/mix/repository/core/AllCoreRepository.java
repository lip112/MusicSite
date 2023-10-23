package com.exam.mix.repository.core;


import com.exam.mix.entity.board.Board;
import com.exam.mix.entity.core.Allcore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AllCoreRepository extends JpaRepository<Allcore, Long> {
    @Query(value = "select b " +
            " from Allcore b " +
            " where b.core_name = :name")
    Optional<Allcore> findByCore_name(@Param("name") String name);
}
