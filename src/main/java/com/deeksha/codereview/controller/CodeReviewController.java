package com.deeksha.codereview.controller;

import com.deeksha.codereview.dto.CreateReviewRequest;
import com.deeksha.codereview.dto.ReviewResponse;
import com.deeksha.codereview.dto.UpdateReviewRequest;
import com.deeksha.codereview.entity.CodeReview;
import com.deeksha.codereview.service.CodeReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
public class CodeReviewController {

    @Autowired
    private CodeReviewService codeReviewService;

    // CREATE REVIEW
    @PostMapping
    public ReviewResponse submitReview(
            @RequestBody @Valid CreateReviewRequest request) {

        CodeReview codeReview = new CodeReview();

        codeReview.setLanguage(request.getLanguage());
        codeReview.setCode(request.getCode());

        CodeReview savedReview =
                codeReviewService.submitReview(codeReview);

        return new ReviewResponse(
                savedReview.getId(),
                savedReview.getLanguage(),
                savedReview.getCode(),
                savedReview.getFeedback()
        );
    }

    // GET ALL REVIEWS
    @GetMapping
    public List<ReviewResponse> getAllReviews() {

        List<CodeReview> reviews =
                codeReviewService.getAllReviews();

        return reviews.stream()
                .map(review -> new ReviewResponse(
                        review.getId(),
                        review.getLanguage(),
                        review.getCode(),
                        review.getFeedback()
                ))
                .toList();
    }

    // GET ONE REVIEW
    @GetMapping("/{id}")
    public ReviewResponse getReviewById(
            @PathVariable Long id) {

        CodeReview review =
                codeReviewService.getReviewById(id);

        return new ReviewResponse(
                review.getId(),
                review.getLanguage(),
                review.getCode(),
                review.getFeedback()
        );
    }

    // UPDATE REVIEW
    @PutMapping("/{id}")
    public ReviewResponse updateReview(
            @PathVariable Long id,
            @RequestBody @Valid UpdateReviewRequest request) {

        CodeReview updatedReview = new CodeReview();

        updatedReview.setLanguage(request.getLanguage());
        updatedReview.setCode(request.getCode());

        CodeReview savedReview =
                codeReviewService.updateReview(id, updatedReview);

        return new ReviewResponse(
                savedReview.getId(),
                savedReview.getLanguage(),
                savedReview.getCode(),
                savedReview.getFeedback()
        );
    }

    // DELETE REVIEW
    @DeleteMapping("/{id}")
    public String deleteReview(@PathVariable Long id) {

        codeReviewService.deleteReview(id);

        return "Review deleted successfully";
    }
}