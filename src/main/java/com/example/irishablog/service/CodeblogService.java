package com.example.irishablog.service;

import com.example.irishablog.model.Post;

import java.util.List;

public interface CodeblogService {
    List<Post> findAll();
    Post findById(Long id);
    Post save(Post post);
}
