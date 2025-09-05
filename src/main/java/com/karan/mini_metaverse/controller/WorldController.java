package com.karan.mini_metaverse.controller;

import com.karan.mini_metaverse.model.WorldEvent;
import com.karan.mini_metaverse.service.WorldService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/world")
public class WorldController {
    private final WorldService service;

    public WorldController(WorldService service) {
        this.service = service;
    }

    @PostMapping("/generateEvent")
    public WorldEvent generate(@RequestParam String context) {
        return service.generateWorldEvent(context);
    }
}

