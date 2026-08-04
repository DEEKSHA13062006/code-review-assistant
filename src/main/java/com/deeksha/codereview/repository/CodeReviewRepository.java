package com.deeksha.codereview.repository;

import com.deeksha.codereview.entity.CodeReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeReviewRepository extends JpaRepository<CodeReview, Long> {

}