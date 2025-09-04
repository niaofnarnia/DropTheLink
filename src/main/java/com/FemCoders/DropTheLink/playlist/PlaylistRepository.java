package com.FemCoders.DropTheLink.playlist;

import com.FemCoders.DropTheLink.playlist.model.Playlist;
import com.FemCoders.DropTheLink.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    List<Playlist> findByUserOrderByIdDesc(User user);
    List<Playlist> findByIsPublishedTrueOrderByIdDesc();
    List<Playlist> findByUserAndIsPublishedTrueOrderByIdDesc(User user);
    List<Playlist> findByNameContainingIgnoreCaseAndIsPublishedTrueOrderByIdDesc(String name);
}
