package com.deeksha.codereview.controller;

import com.deeksha.codereview.entity.CodeReview;
import com.deeksha.codereview.service.CodeReviewService;
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
    public CodeReview submitReview(@RequestBody CodeReview codeReview) {
        return codeReviewService.submitReview(codeReview);
    }

    // GET ALL REVIEWS
    @GetMapping
    public List<CodeReview> getAllReviews() {
        return codeReviewService.getAllReviews();
    }

    // GET ONE REVIEW
    @GetMapping("/{id}")
    public CodeReview getReviewById(@PathVariable Long id) {
        return codeReviewService.getReviewById(id);
    }

    // UPDATE REVIEW
    @PutMapping("/{id}")
    public CodeReview updateReview(
            @PathVariable Long id,
            @RequestBody CodeReview updatedReview) {

        return codeReviewService.updateReview(id, updatedReview);
    }

    // DELETE REVIEW
    @DeleteMapping("/{id}")
    public String deleteReview(@PathVariable Long id) {

        codeReviewService.deleteReview(id);

        return "Review deleted successfully";
    }
}