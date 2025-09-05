package com.karan.mini_metaverse.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class WorldEvent {
    @Id
    @GeneratedValue
    private Long id;
    private String description;
    private String location;
    private String timestamp;
}

