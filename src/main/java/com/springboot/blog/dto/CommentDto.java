package com.springboot.blog.dto;


import lombok.Data;

@Data
public class CommentDto {//we should not expose Comment entity directly to client to we create DTO to show as response

    private long id;
    private String name;
    private String email;
    private String body;

}
