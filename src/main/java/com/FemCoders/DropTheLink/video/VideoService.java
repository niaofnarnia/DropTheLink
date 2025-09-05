package com.FemCoders.DropTheLink.video;

import com.FemCoders.DropTheLink.exceptions.ResourceNotFoundException;
import com.FemCoders.DropTheLink.exceptions.UnauthorizedOperationException;
import com.FemCoders.DropTheLink.playlist.PlaylistRepository;
import com.FemCoders.DropTheLink.playlist.model.Playlist;
import com.FemCoders.DropTheLink.playlist.model.PlaylistVideo;
import com.FemCoders.DropTheLink.playlist.model.PlaylistVideoId;
import com.FemCoders.DropTheLink.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;
    private final PlaylistRepository playlistRepository;

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    @Transactional
    public void addVideoToPlaylist(AddVideoRequest request) {
        Playlist playlist = playlistRepository.findById(request.playlistId())
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found with id: " + request.playlistId()));

        User currentUser = getCurrentUser();
        if (!playlist.getUser().getId().equals(currentUser.getId())) {
            throw new UnauthorizedOperationException("You can only add videos to your own playlists");
        }

        Video video = videoRepository.findByUrl(request.url())
                .orElseGet(() -> {
                    Video newVideo = Video.builder()
                            .title(request.title())
                            .url(request.url())
                            .build();
                    return videoRepository.save(newVideo);
                });

        boolean videoExists = playlist.getVideos().stream()
                .anyMatch(pv -> pv.getVideo().getId().equals(video.getId()));
        if (videoExists) {
            throw new RuntimeException("Video is already in this playlist");
        }

        int nextPosition = playlist.getVideos().size() + 1;

        PlaylistVideo playlistVideo = PlaylistVideo.builder()
                .id(new PlaylistVideoId(playlist.getId(), video.getId()))
                .playlist(playlist)
                .video(video)
                .position(nextPosition)
                .build();

        playlist.getVideos().add(playlistVideo);
        playlistRepository.save(playlist);
    }

    @Transactional
    public void removeVideoFromPlaylist(Long playlistId, Long videoId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found with id: " + playlistId));

        User currentUser = getCurrentUser();
        if(!playlist.getUser().getId().equals(currentUser.getId())) {
            throw new UnauthorizedOperationException("You can only remove videos from your own playlists");
        }

        boolean wasRemoved = playlist.getVideos().removeIf(pv -> pv.getVideo().getId().equals(videoId));

        if(!wasRemoved) {
            throw new ResourceNotFoundException("Video not found in playlist with id: " + playlistId);
        }

        for (int i = 0; i < playlist.getVideos().size(); i++) {
            playlist.getVideos().get(i).setPosition(i + 1);
        }

        boolean isUsedInOtherPlaylists = videoRepository.videoIdNotInOtherPlaylist(videoId, playlistId);

        if(!isUsedInOtherPlaylists) {
            videoRepository.deleteById(videoId);
        }

        playlistRepository.save(playlist);
    }
}