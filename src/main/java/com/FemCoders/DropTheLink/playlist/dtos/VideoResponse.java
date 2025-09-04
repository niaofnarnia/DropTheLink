package com.FemCoders.DropTheLink.playlist.dtos;

public record VideoResponse (
   Long id,
   String title,
   String url,
   Integer position
) {}