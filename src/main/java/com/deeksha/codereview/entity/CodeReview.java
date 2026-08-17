
package com.deeksha.codereview.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "code_reviews")
public class CodeReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String language;

    @Column(columnDefinition = "TEXT")
    private String code;

    @Column(columnDefinition = "TEXT")
    private String feedback;

    private int score;

    @Column(columnDefinition = "TEXT")
    private String findingsJson;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


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

    public int getScore() {
        return score;
    }

    public String getFindingsJson() {
        return findingsJson;
    }

    public User getUser() {
        return user;
    }


    public void setLanguage(String language) {
        this.language = language;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setFindingsJson(String findingsJson) {
        this.findingsJson = findingsJson;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
