package com.oneaston.db.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oneaston.db.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	User findUserByUserId(long userId);
	User findUserByUsername(String username);

}
