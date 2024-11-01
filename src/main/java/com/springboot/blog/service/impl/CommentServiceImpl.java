package com.springboot.blog.service.impl;

import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);

        //retrieve post entity by postId
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));
        //set post to entity {my opinion is we should set postId to which this comment is associated}
        comment.setPost(post);
        //save
        Comment newComment = commentRepository.save(comment);

        return mapToDto(newComment);

    }

    @Override
    public List<CommentDto> fetchAllCommentsByPostId(long postId) {
        List<Comment> commentsByPostId = commentRepository.findByPostId(postId);
        //convert list of Comment entity to CommentDto
        return commentsByPostId.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {

        //retrieve post entity by postId
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

        //retrieve comment by commentId
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment", "id", postId));

        //check if above comment belong to above post
        //business validation
        if (!comment.getPost().getId().equals(post.getId()))throw new BlogApiException(HttpStatus.BAD_REQUEST, "comment doesnot belong to post");

        return mapToDto(comment);

    }

    @Override
    public CommentDto updateCommentById(Long postId, Long commentId, CommentDto commentDto) {
        //retrieve post entity by postId
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

        //retrieve comment by commentId
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment", "id", postId));

        //check if above comment belong to above post
        //business validation
        if (!comment.getPost().getId().equals(post.getId()))throw new BlogApiException(HttpStatus.BAD_REQUEST, "comment doesnot belong to post");

        comment.setName(commentDto.getName());
        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());
        Comment updatedComment = commentRepository.save(comment);
        return mapToDto(updatedComment);
    }

    @Override
    public void deleteCommentById(Long postId, Long commentId) {
        //retrieve post entity by postId
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

        //retrieve comment by commentId
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment", "id", postId));

        //check if above comment belong to above post
        //business validation
        if (!comment.getPost().getId().equals(post.getId()))throw new BlogApiException(HttpStatus.BAD_REQUEST, "comment doesnot belong to post");

        commentRepository.delete(comment);

    }

    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setBody(comment.getBody());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setBody(commentDto.getBody());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        return comment;
    }
}
