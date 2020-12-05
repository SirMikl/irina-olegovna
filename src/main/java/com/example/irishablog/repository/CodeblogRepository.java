package com.example.irishablog.repository;

import com.example.irishablog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeblogRepository extends JpaRepository<Post,Long> {
}
