package com.springboot.blog.repository;

import com.springboot.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//No need to annotate this class with  @Repository because of SimpleJpaRepository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(long postId);
}
