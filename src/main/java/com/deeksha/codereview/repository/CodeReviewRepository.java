package com.deeksha.codereview.repository;

import com.deeksha.codereview.entity.CodeReview;
import com.deeksha.codereview.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CodeReviewRepository extends JpaRepository<CodeReview, Long> {

    List<CodeReview> findByUser(User user);

}