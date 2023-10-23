package com.exam.mix.repository.core;

import com.exam.mix.entity.core.gubun.Davil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DavilRepository extends JpaRepository<Davil, String> {
}
