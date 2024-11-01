package com.springboot.blog.controller;


import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentService commentService;


    //create new comment for post with id = postId
    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable long postId,
                                                    @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);

    }

    //get all comments which belongs to post with id = postId
    @GetMapping("/post/{postId}/comments")
    public ResponseEntity<List<CommentDto>> fetchAllCommentsForPost(@PathVariable long postId){
        return new ResponseEntity<>(commentService.fetchAllCommentsByPostId(postId), HttpStatus.OK);
    }

    //get comment with commentId if it belong to post with id = postId
    @GetMapping("/post/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable long postId,
                                                    @PathVariable long commentId){
        return new ResponseEntity<>(commentService.getCommentById(postId, commentId), HttpStatus.OK);
    }

    //update comment with commentId if it belong to post with id = postId
    @PutMapping("/post/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateCommentById(@PathVariable long postId,
                                                        @PathVariable long commentId,
                                                        @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.updateCommentById(postId, commentId, commentDto), HttpStatus.OK);
    }

    //delete comment
    @DeleteMapping("/post/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteCommentById(@PathVariable long postId,
                                                     @PathVariable long commentId){
        commentService.deleteCommentById(postId, commentId);
        return new ResponseEntity<>("COMMENT HAS BEEN DELETED", HttpStatus.OK);
    }



}
