package com.codewithkk.blog.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.codewithkk.blog.entities.Category;
import com.codewithkk.blog.entities.Post;
import com.codewithkk.blog.entities.User;
import com.codewithkk.blog.exception.ResourceNotFoundException;
import com.codewithkk.blog.payload.PostDto;
import com.codewithkk.blog.payload.PostResponse;
import com.codewithkk.blog.repositories.CategoryRepo;
import com.codewithkk.blog.repositories.PostRepo;
import com.codewithkk.blog.repositories.UserRepo;
import com.codewithkk.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService{
	
	

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto create(PostDto postDto,Integer userId, Integer categoryId) {
		// TODO Auto-generated method stub
		
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(" User ", " id ", userId));
		Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(" Category "," id ", categoryId));
		Post post = modelMapper.map(postDto, Post.class);
		post.setImageName("defaut.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post savePost = postRepo.save(post);
		
		return modelMapper.map(savePost, PostDto.class);
	}

	@Override
	public PostDto update(PostDto postDto, Integer postId) {
		Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException(" Post ", " Id ", postId));
		
		post.setContent(postDto.getContent());
		post.setTitle(postDto.getTitle());
		post.setImageName(postDto.getImageName());
		Post updatedPost = postRepo.save(post);
		return modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void delete(Integer postId) {
	Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException(" Post ", " Id ", postId));
	postRepo.delete(post);
		
	}

	@Override
	public PostDto getPostById(Integer postId) {
		
		Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post ", " Id ", postId));
		PostDto postDto = modelMapper.map(post, PostDto.class);
		
		return postDto;
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy, String sortDir) {
		
		Sort sort = (sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();
		
		Pageable p = PageRequest.of(pageNumber, pageSize,sort);
		
		  Page<Post> pagePost = postRepo.findAll(p);
		  List<Post> posts = pagePost.getContent();
		List<PostDto> postDto = posts.stream().map((post) -> modelMapper.map(post,PostDto.class )).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDto);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category "," Id ", categoryId));
		
		List<Post> posts = postRepo.findByCategory(category);
		
		List<PostDto> postDto = posts.stream().map((post) -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDto;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
	User users = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User ", " Id ", userId));
	List<Post> posts = postRepo.findByUser(users);
	List<PostDto> postDto = posts.stream().map((post) -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
	
		return postDto;
	}

	@Override
	public List<PostDto> searchPost(String keyword) {
		// TODO Auto-generated method stub
		List<Post> posts = postRepo.findByTitleContaining(keyword);
		
		List<PostDto> postDto = posts.stream().map((post) -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDto;
	}

}
