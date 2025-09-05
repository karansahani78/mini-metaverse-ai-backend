package com.karan.mini_metaverse.service;

import com.karan.mini_metaverse.exceptions.PlayerNotFoundException;
import com.karan.mini_metaverse.model.Player;
import com.karan.mini_metaverse.repository.PlayerRepository;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final AIService aiService;
    public PlayerService(PlayerRepository playerRepository, AIService aiService) {
        this.playerRepository = playerRepository;
        this.aiService = aiService;
    }
    public Player createPlayer(Player player) {
        Player p= new Player();
        p.setName(player.getName());
        p.setLocation(player.getLocation());
        p.setInventory(player.getInventory());
      return  playerRepository.save(p);
    }
    // player action
    public String playerAction(Long playerId, String action) {
        Player player = playerRepository.findById(playerId).orElseThrow(()-> new PlayerNotFoundException("player not found for given id"+playerId));
        // AI generates result based on action
        return aiService.generateEvent("Player " + player.getName() + " performs action: " + action);

    }

}
