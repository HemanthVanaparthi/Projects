package com.ms.registration.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.registration.dao.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	User findByName(String name);
}
