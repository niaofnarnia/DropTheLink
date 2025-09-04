package com.FemCoders.DropTheLink.playlist.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdatePlaylistRequest (
        @NotBlank(message = "Playlist name cannot be blank")
        @Size(max = 100, message = "Playlist name must not exceed 100 characters")
        String name,

        @Size(max = 500, message = "Description must not exceed 500 characters")
        String description,

        Boolean isPublished
){}