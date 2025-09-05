package com.FemCoders.DropTheLink.video;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    Optional<Video> findByUrl(String url);
    @Query("SELECT COUNT(pv) > 0 FROM PlaylistVideo pv WHERE pv.video.id = ?1 AND pv.playlist.id <> ?2")
    boolean videoIdNotInOtherPlaylist(Long videoId, Long playlistId);
}
