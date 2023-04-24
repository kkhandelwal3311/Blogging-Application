package com.codewithkk.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codewithkk.blog.config.AppConstants;
import com.codewithkk.blog.payload.ApiResponse;
import com.codewithkk.blog.payload.PostDto;
import com.codewithkk.blog.payload.PostResponse;
import com.codewithkk.blog.service.PostService;

import net.bytebuddy.implementation.bytecode.constant.DefaultValue;

@RestController
@RequestMapping("/api")
public class PostController {
	
	@Autowired
	private PostService postService;

	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId){
		
	
		PostDto  createPost = postService.create(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
	}
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId){
		
		List<PostDto> posts = postService.getPostByUser(userId);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId){
		List<PostDto> posts = postService.getPostByCategory(categoryId);
		
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
		PostDto postDto = postService.getPostById(postId);
		return new ResponseEntity<PostDto>(postDto,HttpStatus.OK);
	}
	
	@GetMapping("/posts/")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam (value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
			@RequestParam (value ="pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam (value ="sortBy",defaultValue =AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam (value ="sortDir",defaultValue =AppConstants.SORT_DIR, required = false) String sortDir){
		 PostResponse postResponse = postService.getAllPost(pageNumber, pageSize,sortBy,sortDir);
		return new ResponseEntity<PostResponse> (postResponse,HttpStatus.OK);
	}
	
	@DeleteMapping("/posts/{postId}")
	public ApiResponse deletePost(@PathVariable Integer postId) {
		postService.delete(postId);
		return new ApiResponse(" Post deleted successfully !", true);
	}
	
	@PutMapping("/posts/{postId}/")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDTo, @PathVariable Integer postId){
		PostDto updatedPost = postService.update(postDTo, postId);
		return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);
	}
	
	@GetMapping("/posts/search/{keyword}/")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keyword){
		
		List<PostDto> result = postService.searchPost(keyword);
		return new ResponseEntity<List<PostDto>> (result,HttpStatus.OK);
	}
}
