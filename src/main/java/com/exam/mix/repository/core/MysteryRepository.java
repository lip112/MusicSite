package com.exam.mix.repository.core;

import com.exam.mix.entity.core.gubun.Mystery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MysteryRepository extends JpaRepository<Mystery, String> {
}
