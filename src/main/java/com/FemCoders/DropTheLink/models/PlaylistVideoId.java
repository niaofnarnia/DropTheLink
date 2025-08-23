package com.FemCoders.DropTheLink.models;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Embeddable
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
public class PlaylistVideoId implements Serializable{
    @Column(name = "playlist_id")
    private Long playlistId;

    @Column(name = "video_id")
    private Long videoId;
}