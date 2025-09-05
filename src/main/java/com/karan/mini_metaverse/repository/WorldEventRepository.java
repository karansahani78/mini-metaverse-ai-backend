package com.karan.mini_metaverse.repository;

import com.karan.mini_metaverse.model.WorldEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorldEventRepository extends JpaRepository<WorldEvent, Long> {}
