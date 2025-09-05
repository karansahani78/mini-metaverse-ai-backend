package com.karan.mini_metaverse.controller;

import com.karan.mini_metaverse.service.NPCService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/npcs")
public class NPCController {
    private final NPCService service;

    public NPCController(NPCService service) {
        this.service = service;
    }

    @PostMapping("/{id}/act")
    public String act(@PathVariable Long id) {
        return service.npcAct(id);
    }
}
