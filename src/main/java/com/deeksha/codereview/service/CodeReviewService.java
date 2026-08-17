
package com.deeksha.codereview.service;

import com.deeksha.codereview.dto.AnalysisResult;
import com.deeksha.codereview.dto.CodeFinding;
import com.deeksha.codereview.exception.ForbiddenException;
import com.deeksha.codereview.exception.ResourceNotFoundException;
import com.deeksha.codereview.entity.CodeReview;
import com.deeksha.codereview.entity.User;
import com.deeksha.codereview.repository.CodeReviewRepository;
import com.deeksha.codereview.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private ObjectMapper objectMapper;


    // CREATE REVIEW
    public CodeReview submitReview(CodeReview codeReview) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        codeReview.setUser(user);

        AnalysisResult analysisResult =
                codeAnalyzerService.analyze(codeReview.getCode());

        applyAnalysis(codeReview, analysisResult);

        return codeReviewRepository.save(codeReview);
    }


    // GET ALL REVIEWS
    public List<CodeReview> getAllReviews() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return codeReviewRepository.findByUser(user);
    }


    // GET ONE REVIEW
    public CodeReview getReviewById(Long id) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        CodeReview review = codeReviewRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Review not found"));

        if (!review.getUser().getId().equals(user.getId())) {
            throw new ForbiddenException(
                    "You are not authorized to access this review"
            );
        }

        return review;
    }


    // DELETE REVIEW
    public void deleteReview(Long id) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        CodeReview review = codeReviewRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Review not found"));

        if (!review.getUser().getId().equals(user.getId())) {
            throw new ForbiddenException(
                    "You are not authorized to delete this review"
            );
        }

        codeReviewRepository.deleteById(id);
    }


    // UPDATE REVIEW
    public CodeReview updateReview(Long id, CodeReview updatedReview) {

        CodeReview existingReview = codeReviewRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Review not found"));

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

       

        if (!existingReview.getUser().getId().equals(user.getId())) {
            throw new ForbiddenException(
                    "You are not authorized to update this review"
            );
        }

        existingReview.setLanguage(updatedReview.getLanguage());
        existingReview.setCode(updatedReview.getCode());

        AnalysisResult analysisResult =
                codeAnalyzerService.analyze(updatedReview.getCode());

        applyAnalysis(existingReview, analysisResult);

        return codeReviewRepository.save(existingReview);
    }


    // Apply analyzer result to the review entity
    private void applyAnalysis(
            CodeReview codeReview,
            AnalysisResult analysisResult) {

        codeReview.setScore(analysisResult.getScore());

        try {
            codeReview.setFindingsJson(
                    objectMapper.writeValueAsString(
                            analysisResult.getFindings()
                    )
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(
                    "Failed to store analysis findings"
            );
        }

        codeReview.setFeedback(
                formatFeedback(analysisResult)
        );
    }


    // Convert structured analysis into the existing feedback String
    private String formatFeedback(AnalysisResult analysisResult) {

        StringBuilder feedback = new StringBuilder();

        feedback.append("Score: ")
                .append(analysisResult.getScore())
                .append("/100. ");

        List<CodeFinding> findings =
                analysisResult.getFindings();

        if (findings == null || findings.isEmpty()) {

            feedback.append(
                    "Code looks clean. No major issues found."
            );

            return feedback.toString();
        }

        for (CodeFinding finding : findings) {

            feedback.append("[")
                    .append(finding.getSeverity())
                    .append(" - ")
                    .append(finding.getCategory())
                    .append("] ")
                    .append(finding.getMessage())
                    .append(" ");
        }

        return feedback.toString().trim();
    }
}
