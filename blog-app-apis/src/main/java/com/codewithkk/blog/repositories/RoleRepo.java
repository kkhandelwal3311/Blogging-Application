package com.codewithkk.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithkk.blog.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{

}
