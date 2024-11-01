package com.springboot.blog.helper;

import com.springboot.blog.controller.CommentController;
import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.dto.PostDto;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class MapperHelper {

    @Autowired
    private CommentRepository commentRepository;

    public static Post mapToEntity(PostDto postDto){
        Set<Comment> commentSet = postDto.getComments().stream().map(commentDto -> mapCommentDtoToEntity(commentDto)).collect(Collectors.toSet());
        Post post = Post.builder()
                .title(postDto.getTitle())
                .description(postDto.getDescription())
                .content(postDto.getContent())
                .comments(commentSet)
                .build();
        return post;
    }

    public PostDto mapToDto(Post post){
        List<Comment> byPostId = commentRepository.findByPostId(post.getId());

        Set<CommentDto> commentDtoSet = byPostId.stream().map(comment -> mapCommentToDto(comment)).collect(Collectors.toSet());
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .description(post.getDescription())
                .content(post.getContent())
                .comments(commentDtoSet)
                .build();
    }

    public static CommentDto mapCommentToDto(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setBody(comment.getBody());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        return commentDto;
    }

    public static Comment mapCommentDtoToEntity(CommentDto commentDto){
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setBody(commentDto.getBody());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        return comment;
    }
}
