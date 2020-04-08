package com.example.todolistapp.repository;

import com.example.todolistapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsernameAndActive(String username,Boolean active);
    User findByEmailAndActive(String email,Boolean active);
}
