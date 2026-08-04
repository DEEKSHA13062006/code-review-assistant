package com.deeksha.codereview.controller;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.deeksha.codereview.entity.CodeReview;
import com.deeksha.codereview.service.CodeReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
public class CodeReviewController {

    @Autowired
    private CodeReviewService codeReviewService;

    @PostMapping
    public CodeReview reviewCode(@RequestBody CodeReview codeReview) {
        return codeReviewService.submitReview(codeReview);
    }
    @GetMapping
public List<CodeReview> getAllReviews() {
    return codeReviewService.getAllReviews();
}
@GetMapping("/{id}")
public CodeReview getReviewById(@PathVariable Long id) {
    return codeReviewService.getReviewById(id);
}
@DeleteMapping("/{id}")
public String deleteReview(@PathVariable Long id) {

    codeReviewService.deleteReview(id);

    return "Review deleted successfully";
}
}