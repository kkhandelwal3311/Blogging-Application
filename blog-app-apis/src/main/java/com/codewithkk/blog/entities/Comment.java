package com.codewithkk.blog.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int id;
	private String content;
	@ManyToOne
	private Post post;
}
