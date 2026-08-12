
package com.deeksha.codereview.service;

import org.springframework.security.core.context.SecurityContextHolder;

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

    String email = SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getName();

    User user = userRepository.findByEmail(email)
            .orElseThrow(() ->
                    new RuntimeException("User not found"));

    codeReview.setUser(user);

    String feedback =
            codeAnalyzerService.analyze(codeReview.getCode());

    codeReview.setFeedback(feedback);

    return codeReviewRepository.save(codeReview);
}


    public List<CodeReview> getAllReviews() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return codeReviewRepository.findByUser(user);
    }


    public CodeReview getReviewById(Long id) {

    String email = SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getName();

    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

    CodeReview review = codeReviewRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Review not found"));

    if (!review.getUser().getId().equals(user.getId())) {
        throw new RuntimeException("You are not allowed to access this review");
    }

    return review;
}


    public void deleteReview(Long id) {

        codeReviewRepository.deleteById(id);
    }


    public CodeReview updateReview(Long id, CodeReview updatedReview) {

        CodeReview existingReview = codeReviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        existingReview.setLanguage(updatedReview.getLanguage());
        existingReview.setCode(updatedReview.getCode());

        String feedback =
                codeAnalyzerService.analyze(updatedReview.getCode());

        existingReview.setFeedback(feedback);

        return codeReviewRepository.save(existingReview);
    }
}
