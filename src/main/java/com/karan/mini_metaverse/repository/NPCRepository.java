package com.karan.mini_metaverse.repository;

import com.karan.mini_metaverse.model.NPC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NPCRepository extends JpaRepository<NPC, Long> {}