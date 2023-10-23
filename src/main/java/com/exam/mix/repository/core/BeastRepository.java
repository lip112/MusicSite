package com.exam.mix.repository.core;

import com.exam.mix.entity.core.gubun.Beast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeastRepository extends JpaRepository<Beast, String> {
}
