package com.springboot.blog.service.impl;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.dto.PostResponse;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.helper.MapperHelper;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service//It is available for autodetection while component scanning
public class PostServiceImpl implements PostService {

    @Autowired
    private MapperHelper mapperHelper;

    @Autowired
    private PostRepository postRepository;



    @Override
    public PostDto createPost(PostDto postDto) {
        //convert dto to entity
//        Post post = Post.builder()
//                .title(postDto.getTitle())
//                .description(postDto.getDescription())
//                .content(postDto.getContent())
//                .build();
        Post post = mapperHelper.mapToEntity(postDto);
        Post newPost = postRepository.save(post);

        //convert entity to dto
//        return PostDto.builder()
//                .id(newPost.getId())
//                .title(newPost.getTitle())
//                .description(newPost.getDescription())
//                .content(newPost.getContent())
//                .build();
        return mapperHelper.mapToDto(newPost);
    }

    @Override
    public PostResponse getAllPosts(int pageNo,int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        //create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

//        List<Post> allPosts = postRepository.findAll();
        Page<Post> allPosts = postRepository.findAll(pageable);

        //get content from page object
        List<Post> listOfPostContent = allPosts.getContent();
        List<PostDto> content = listOfPostContent.stream().map(post -> mapperHelper.mapToDto(post)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(allPosts.getNumber());
        postResponse.setPageSize(allPosts.getSize());
        postResponse.setTotalElements(allPosts.getTotalElements());
        postResponse.setTotalPages(allPosts.getTotalPages());
        postResponse.setLastPage(allPosts.isLast());
        return postResponse;

    }

    @Override
    public PostDto getPostById(long id) {
        Post postById = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("POST", "id", id));
        return mapperHelper.mapToDto(postById);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post postById = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("POST", "id", id));
        postById.setTitle(postDto.getTitle());
        postById.setDescription(postDto.getDescription());
        postById.setContent(postById.getContent());
        Post updatedPost = postRepository.save(postById);
        return mapperHelper.mapToDto(updatedPost);
    }

    @Override
    public void deletePostById(long id) {
        Post postById = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("POST", "id", id));
        postRepository.delete(postById);
    }


}
