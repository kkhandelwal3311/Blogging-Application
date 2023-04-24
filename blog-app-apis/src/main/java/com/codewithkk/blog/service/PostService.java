package com.codewithkk.blog.service;

import java.util.List;

import com.codewithkk.blog.entities.Post;
import com.codewithkk.blog.payload.PostDto;
import com.codewithkk.blog.payload.PostResponse;

public interface PostService {

	PostDto create(PostDto postDto,Integer userId, Integer categoryId);
	
	PostDto update(PostDto postDto, Integer postId);
	
	void delete(Integer postId);
	
	PostDto getPostById(Integer postId);
	
	PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
	List<PostDto> getPostByCategory(Integer categoryId);
	
	List<PostDto> getPostByUser(Integer userId);
	
	List<PostDto> searchPost(String keyword);

}
