package com.exam.mix.repository.core;

import com.exam.mix.entity.core.gubun.Metal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetalRepository extends JpaRepository<Metal, String> {
}
