package com.deeksha.codereview.repository;

import com.deeksha.codereview.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Kept for UserService and CustomUserDetailsService
    Optional<User> findByEmail(String email);
    
    // Used by CodeReviewService for case-insensitive safety
    Optional<User> findByEmailIgnoreCase(String email);
}