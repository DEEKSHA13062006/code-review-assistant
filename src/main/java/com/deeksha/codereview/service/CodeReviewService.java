package com.deeksha.codereview.service;

import com.deeksha.codereview.entity.CodeReview;
import com.deeksha.codereview.entity.User;
import com.deeksha.codereview.repository.CodeReviewRepository;
import com.deeksha.codereview.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodeReviewService {

    @Autowired
    private CodeReviewRepository codeReviewRepository;

    @Autowired
    private CodeAnalyzerService codeAnalyzerService;

    @Autowired
    private UserRepository userRepository;


    public CodeReview submitReview(CodeReview codeReview) {

        // JWT TEMPORARILY DISABLED
        // Use the first registered user for the review.

        User user = userRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("No user found in database"));

        codeReview.setUser(user);

        String feedback =
                codeAnalyzerService.analyze(codeReview.getCode());

        codeReview.setFeedback(feedback);

        return codeReviewRepository.save(codeReview);
    }


    public List<CodeReview> getAllReviews() {

        return codeReviewRepository.findAll();
    }


    public CodeReview getReviewById(Long id) {

        return codeReviewRepository.findById(id)
                .orElse(null);
    }


    public void deleteReview(Long id) {

        codeReviewRepository.deleteById(id);
    }
    public CodeReview updateReview(Long id, CodeReview updatedReview) {

    CodeReview existingReview = codeReviewRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Review not found"));

    existingReview.setLanguage(updatedReview.getLanguage());
    existingReview.setCode(updatedReview.getCode());

    String feedback = codeAnalyzerService.analyze(updatedReview.getCode());
    existingReview.setFeedback(feedback);

    return codeReviewRepository.save(existingReview);
}
}