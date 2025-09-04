package com.FemCoders.DropTheLink.video;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AddVideoRequest(
        @NotNull(message = "Playlist ID is required")
        Long playlistId,

        @NotBlank(message = "Video title cannot be blank")
        String title,

        @NotBlank(message = "Video URL cannot be blank")
        @Pattern(regexp = "^(https?://)?(www\\.)?(youtube\\.com/(watch\\?v=|embed/)|youtu\\.be/)([a-zA-Z0-9_-]{11}).*$",
                message = "Please provide a valid YouTube URL")
        String url
) {}