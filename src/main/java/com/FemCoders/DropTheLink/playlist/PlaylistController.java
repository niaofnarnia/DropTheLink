package com.FemCoders.DropTheLink.playlist;

import com.FemCoders.DropTheLink.playlist.dtos.CreatePlaylistRequest;
import com.FemCoders.DropTheLink.playlist.dtos.PlaylistResponse;
import com.FemCoders.DropTheLink.playlist.dtos.UpdatePlaylistRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlists")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;

    @PostMapping
    public ResponseEntity<PlaylistResponse> createPlaylist(@Valid @RequestBody CreatePlaylistRequest request) {
        PlaylistResponse response = playlistService.createPlaylist(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/my")
    public ResponseEntity<List<PlaylistResponse>> getUserPlaylists() {
        List<PlaylistResponse> playlists = playlistService.getUserPlaylists();
        return ResponseEntity.ok(playlists);
    }

    @GetMapping("/public")
    public ResponseEntity<List<PlaylistResponse>> getPublishedPlaylists() {
        List<PlaylistResponse> playlists = playlistService.getPublishedPlaylists();
        return ResponseEntity.ok(playlists);
    }

    @GetMapping("/search")
    public ResponseEntity<List<PlaylistResponse>> searchPlaylistsByName(@RequestParam String name) {
        List<PlaylistResponse> playlists = playlistService.searchPlaylistsByName(name);
        return ResponseEntity.ok(playlists);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaylistResponse> getPlaylistById(@PathVariable Long id) {
        PlaylistResponse playlist = playlistService.getPlaylistById(id);
        return ResponseEntity.ok(playlist);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaylistResponse> updatePlaylist(@PathVariable Long id, @Valid @RequestBody UpdatePlaylistRequest request) {
        PlaylistResponse response = playlistService.updatePlaylist(id, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/toggle-publish")
    public ResponseEntity<PlaylistResponse> togglePublishStatus(@PathVariable Long id) {
        PlaylistResponse response = playlistService.togglePublishStatus(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePlaylist(@PathVariable Long id) {
        playlistService.deletePlaylist(id);
        return ResponseEntity.ok("Playlist deleted successfully!");
    }
}