package com.deeksha.codereview.service;

import java.util.List;
import com.deeksha.codereview.entity.CodeReview;
import com.deeksha.codereview.repository.CodeReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CodeReviewService {

    @Autowired
    private CodeReviewRepository codeReviewRepository;

    @Autowired
    private CodeAnalyzerService codeAnalyzerService;

    public CodeReview submitReview(CodeReview codeReview) {

        String feedback = codeAnalyzerService.analyze(codeReview.getCode());

        codeReview.setFeedback(feedback);

        return codeReviewRepository.save(codeReview);
    }
    public List<CodeReview> getAllReviews() {
    return codeReviewRepository.findAll();
}
public CodeReview getReviewById(Long id) {
    return codeReviewRepository.findById(id).orElse(null);
}
public void deleteReview(Long id) {
    codeReviewRepository.deleteById(id);
}
}