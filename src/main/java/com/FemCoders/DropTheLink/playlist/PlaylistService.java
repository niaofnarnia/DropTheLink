package com.FemCoders.DropTheLink.playlist;

import com.FemCoders.DropTheLink.exceptions.ResourceNotFoundException;
import com.FemCoders.DropTheLink.exceptions.UnauthorizedOperationException;
import com.FemCoders.DropTheLink.playlist.dtos.CreatePlaylistRequest;
import com.FemCoders.DropTheLink.playlist.dtos.PlaylistResponse;
import com.FemCoders.DropTheLink.playlist.dtos.UpdatePlaylistRequest;
import com.FemCoders.DropTheLink.playlist.dtos.VideoResponse;
import com.FemCoders.DropTheLink.playlist.model.Playlist;
import com.FemCoders.DropTheLink.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaylistService {

    private final PlaylistRepository playlistRepository;

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    private PlaylistResponse mapToResponse (Playlist playlist) {
        List<VideoResponse> videoResponses = playlist.getVideos().stream()
                .map(pv -> new VideoResponse(
                        pv.getVideo().getId(),
                        pv.getVideo().getTitle(),
                        pv.getVideo().getUrl(),
                        pv.getPosition()
                ))
                .collect(Collectors.toList());

        return new PlaylistResponse(
                playlist.getId(),
                playlist.getName(),
                playlist.getDescription(),
                playlist.getIsPublished(),
                playlist.getUser().getUsername(),
                videoResponses
        );
    }

    @Transactional
    public PlaylistResponse createPlaylist(CreatePlaylistRequest request) {
        User currentUser = getCurrentUser();

        Playlist playlist = Playlist.builder()
                .name(request.name())
                .description(request.description())
                .isPublished(false)
                .user(currentUser)
                .build();

        Playlist savedPlaylist = playlistRepository.save(playlist);
        return mapToResponse(savedPlaylist);
    }

    public List<PlaylistResponse> getUserPlaylists() {
        User currentUser = getCurrentUser();
        List<Playlist> playlists = playlistRepository.findByUserOrderByIdDesc(currentUser);
        return playlists.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<PlaylistResponse> getPublishedPlaylists() {
        List<Playlist> playlists = playlistRepository.findByIsPublishedTrueOrderByIdDesc();
        return playlists.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<PlaylistResponse> searchPlaylistsByName(String name) {
        List<Playlist> playlists = playlistRepository.findByNameContainingIgnoreCaseAndIsPublishedTrueOrderByIdDesc(name);
        return playlists.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public PlaylistResponse getPlaylistById(Long id) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found with id: " + id));

        User currentUser = getCurrentUser();

        if (!playlist.getIsPublished() && !playlist.getUser().getId().equals(currentUser.getId())) {
            throw new UnauthorizedOperationException("Unable to access this playlist");
        }

        return mapToResponse(playlist);
    }

    @Transactional
    public PlaylistResponse updatePlaylist(Long id, UpdatePlaylistRequest request) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found with id: " + id));

        User currentUser = getCurrentUser();
        if(!playlist.getUser().getId().equals(currentUser.getId())) {
            throw new UnauthorizedOperationException("You can only update your own playlists");
        }

        playlist.setName(request.name());
        playlist.setDescription(request.description());
        if (request.isPublished() != null) {
            playlist.setIsPublished(request.isPublished());
        }

        Playlist savedPlaylist = playlistRepository.save(playlist);
        return mapToResponse(savedPlaylist);
    }

    @Transactional
    public void deletePlaylist(Long id) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found with id: " + id));

        User currentUser = getCurrentUser();
        if (!playlist.getUser().getId().equals(currentUser.getId())) {
            throw new UnauthorizedOperationException("You can only delete your own playlists");
        }

        playlistRepository.delete(playlist);
    }

    @Transactional
    public PlaylistResponse togglePublishStatus(Long id) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found with id: " + id));

        User currentuser = getCurrentUser();
        if (!playlist.getUser().getId().equals(currentuser.getId())) {
            throw new UnauthorizedOperationException("You can only modify your own playlists");
        }

        playlist.setIsPublished(!playlist.getIsPublished());
        Playlist savedPlaylist = playlistRepository.save(playlist);
        return mapToResponse(savedPlaylist);
    }
}
