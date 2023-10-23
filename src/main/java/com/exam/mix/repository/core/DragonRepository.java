package com.exam.mix.repository.core;

import com.exam.mix.entity.core.gubun.Dragon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DragonRepository extends JpaRepository<Dragon, String> {
}
