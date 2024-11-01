package com.springboot.blog.repository;

import com.springboot.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

//No need to annotate it with @Repository because inside SimpleJpaRepository class it extends JpaRepository which already has @Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
