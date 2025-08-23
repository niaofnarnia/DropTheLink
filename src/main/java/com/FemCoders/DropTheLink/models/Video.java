package com.FemCoders.DropTheLink.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "videos")
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@Builder

public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name = "video_url", nullable = false)
    private String url;

    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<PlaylistVideo> playlists = new ArrayList<>();
}
