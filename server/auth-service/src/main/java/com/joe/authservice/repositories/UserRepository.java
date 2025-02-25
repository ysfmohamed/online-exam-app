package com.joe.authservice.repositories;

import com.joe.authservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByUsername(String username);
}
