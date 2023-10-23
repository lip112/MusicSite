package com.exam.mix.repository.core;

import com.exam.mix.entity.core.gubun.Bug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BugRepository extends JpaRepository<Bug, String> {
}
