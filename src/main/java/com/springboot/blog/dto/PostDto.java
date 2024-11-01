package com.springboot.blog.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class PostDto {
    private long id;

    @NotEmpty
    @Size(min = 2, message = "Post title should be at least 2 characters")
    private String title;

    @NotEmpty
    @Size(min = 2, message = "Post description should be at least 2 characters")
    private String description;

    @NotEmpty
    @Size(min = 2, message = "Post content should be at least 2 characters")
    private String content;
    private Set<CommentDto> comments;
}
