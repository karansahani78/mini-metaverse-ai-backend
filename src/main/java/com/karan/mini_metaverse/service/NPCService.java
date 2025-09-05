package com.karan.mini_metaverse.service;

import com.karan.mini_metaverse.model.NPC;
import com.karan.mini_metaverse.repository.NPCRepository;
import org.springframework.stereotype.Service;

@Service
public class NPCService {
    private final NPCRepository repo;
    private final AIService aiService;

    public NPCService(NPCRepository repo, AIService aiService) {
        this.repo = repo;
        this.aiService = aiService;
    }
    public String npcAct(Long npcId){
        NPC npc = repo.findById(npcId).orElseThrow();
        return aiService.generateEvent("NPC " + npc.getName() + " with goal " + npc.getGoal() + " takes action");

    }
}
