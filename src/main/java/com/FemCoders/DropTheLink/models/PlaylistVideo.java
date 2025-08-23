package com.FemCoders.DropTheLink.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "playlist_video")
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@Builder

public class PlaylistVideo {
    private @EmbeddedId PlaylistVideoId id;

    private int position;
}