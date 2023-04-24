package com.codewithkk.blog.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithkk.blog.entities.Comment;
import com.codewithkk.blog.entities.Post;
import com.codewithkk.blog.exception.ResourceNotFoundException;
import com.codewithkk.blog.payload.CommentDto;
import com.codewithkk.blog.repositories.CommentRepo;
import com.codewithkk.blog.repositories.PostRepo;
import com.codewithkk.blog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentRepo commentRepo;
	@Autowired
	 private PostRepo postRepo;
	@Autowired
	 private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		
		Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post ", " id" , postId));
		
		Comment comment = modelMapper.map(commentDto, Comment.class);
		
		comment.setPost(post);
		Comment savedComment = commentRepo.save(comment);
		
		
		return modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		// TODO Auto-generated method stub
		Comment comment = commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", " Id ", commentId));
		commentRepo.delete(comment);
		
	}

}
