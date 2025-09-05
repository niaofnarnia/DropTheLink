package com.FemCoders.DropTheLink.video;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @PostMapping
    public ResponseEntity<String> addVideoToPlaylist(@Valid @RequestBody AddVideoRequest request) {
        videoService.addVideoToPlaylist(request);
        return new ResponseEntity<>("Video added to playlist successfully!", HttpStatus.CREATED);
    }

    @DeleteMapping("/playlist/{playlistId}/video/{videoId}")
    public ResponseEntity<String> removeVideoFromPlaylist(
            @PathVariable Long playlistId,
            @PathVariable Long videoId) {
        videoService.removeVideoFromPlaylist(playlistId, videoId);
        return ResponseEntity.ok("Video removed successfully from playlist!");
    }
}