package com.karan.mini_metaverse.service;

import com.karan.mini_metaverse.model.WorldEvent;
import com.karan.mini_metaverse.repository.WorldEventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class WorldService {

    private final WorldEventRepository repo;
    private final AIService aiService;

    public WorldService(WorldEventRepository repo, AIService aiService) {
        this.repo = repo;
        this.aiService = aiService;
    }

    public WorldEvent generateWorldEvent(String context) {
        String description = aiService.generateEvent("Generate world event: " + context);
        WorldEvent event = new WorldEvent();
        event.setDescription(description);
        event.setTimestamp(LocalDateTime.now().toString());
        event.setLocation("Global");
        return repo.save(event);
    }
}

