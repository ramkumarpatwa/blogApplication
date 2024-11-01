package com.springboot.blog.service;

import com.springboot.blog.dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
    List<CommentDto> fetchAllCommentsByPostId(long postId);
    CommentDto getCommentById(Long postId, Long commnetId);
    CommentDto updateCommentById(Long postId, Long commentId, CommentDto commentDto);
    void deleteCommentById(Long postId, Long commentId);
}
