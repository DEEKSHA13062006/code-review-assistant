package com.deeksha.codereview.dto;

public class ReviewResponse {

    private Long id;
    private String language;
    private String code;
    private String feedback;

    public ReviewResponse(Long id, String language, String code, String feedback) {
        this.id = id;
        this.language = language;
        this.code = code;
        this.feedback = feedback;
    }

    public Long getId() {
        return id;
    }

    public String getLanguage() {
        return language;
    }

    public String getCode() {
        return code;
    }

    public String getFeedback() {
        return feedback;
    }
}