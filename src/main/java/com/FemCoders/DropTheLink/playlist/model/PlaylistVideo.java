package com.FemCoders.DropTheLink.playlist.model;

import com.FemCoders.DropTheLink.video.Video;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "playlist_video")
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@Builder

public class PlaylistVideo {

    @EmbeddedId
    private PlaylistVideoId id;

    private int position;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("playlistId")
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("videoId")
    @JoinColumn(name = "video_id")
    private Video video;
}