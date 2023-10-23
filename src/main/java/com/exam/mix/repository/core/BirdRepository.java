package com.exam.mix.repository.core;

import com.exam.mix.entity.core.gubun.Bird;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BirdRepository extends JpaRepository<Bird, String> {
}
