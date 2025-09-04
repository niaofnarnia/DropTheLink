package com.FemCoders.DropTheLink.playlist.dtos;

import java.util.List;

public record PlaylistResponse (
        Long id,
        String name,
        String description,
        Boolean isPublished,
        String ownerUsername,
        List<VideoResponse> videos
){}