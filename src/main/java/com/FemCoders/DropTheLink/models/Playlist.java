package com.FemCoders.DropTheLink.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "playlists")
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column(nullable = false)
    private Boolean isPublished = false;
}
