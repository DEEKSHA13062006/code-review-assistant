package com.deeksha.codereview.controller;

import com.deeksha.codereview.dto.CreateReviewRequest;
import com.deeksha.codereview.dto.ReviewResponse;
import com.deeksha.codereview.dto.UpdateReviewRequest;
import com.deeksha.codereview.entity.CodeReview;
import com.deeksha.codereview.service.CodeReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/review")
@SecurityRequirement(name = "bearerAuth")
public class CodeReviewController {

    @Autowired
    private CodeReviewService codeReviewService;

    // CREATE REVIEW
    @Operation(
    summary = "Submit a code review",
    description = "Submits code for analysis and creates a review for the authenticated user."
)
@ApiResponses({
    @ApiResponse(responseCode = "200", description = "Review created successfully"),
    @ApiResponse(responseCode = "400", description = "Language or code is missing"),
    @ApiResponse(responseCode = "401", description = "Authentication required")
})
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
@Operation(
    summary = "Get all reviews",
    description = "Returns all code reviews belonging to the authenticated user."
)
@ApiResponses({
    @ApiResponse(responseCode = "200", description = "Reviews retrieved successfully"),
    @ApiResponse(responseCode = "401", description = "Authentication required")
})
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
@Operation(
    summary = "Get a review by ID",
    description = "Returns a review only if it belongs to the authenticated user."
)
@ApiResponses({
    @ApiResponse(responseCode = "200", description = "Review retrieved successfully"),
    @ApiResponse(responseCode = "401", description = "Authentication required"),
    @ApiResponse(responseCode = "403", description = "User is not authorized to access this review"),
    @ApiResponse(responseCode = "404", description = "Review not found")
})
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
    @Operation(
    summary = "Update a review",
    description = "Updates a review only if it belongs to the authenticated user."
)
@ApiResponses({
    @ApiResponse(responseCode = "200", description = "Review updated successfully"),
    @ApiResponse(responseCode = "400", description = "Invalid request data"),
    @ApiResponse(responseCode = "401", description = "Authentication required"),
    @ApiResponse(responseCode = "403", description = "User is not authorized to update this review"),
    @ApiResponse(responseCode = "404", description = "Review not found")
})
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
    @Operation(
    summary = "Delete a review",
    description = "Deletes a review only if it belongs to the authenticated user."
)
@ApiResponses({
    @ApiResponse(responseCode = "200", description = "Review deleted successfully"),
    @ApiResponse(responseCode = "401", description = "Authentication required"),
    @ApiResponse(responseCode = "403", description = "User is not authorized to delete this review"),
    @ApiResponse(responseCode = "404", description = "Review not found")
})
    @DeleteMapping("/{id}")
    public String deleteReview(@PathVariable Long id) {

        codeReviewService.deleteReview(id);

        return "Review deleted successfully";
    }
}