package com.codewithkk.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithkk.blog.entities.Comment;

public interface CommentRepo  extends JpaRepository<Comment, Integer>{

}
