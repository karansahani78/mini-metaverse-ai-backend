package com.karan.mini_metaverse.controller;

import com.karan.mini_metaverse.model.Player;
import com.karan.mini_metaverse.service.PlayerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/players")
public class PlayerController {
    private final PlayerService service;

    public PlayerController(PlayerService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public Player create(@RequestBody Player player) {
        return service.createPlayer(player);
    }

    @PostMapping("/{id}/action")
    public String action(@PathVariable Long id, @RequestParam String action) {
        return service.playerAction(id, action);
    }
}
