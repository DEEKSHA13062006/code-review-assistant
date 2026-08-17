package com.deeksha.codereview.dto;

import java.util.List;

public class AnalysisResult {

    private int score;
    private List<CodeFinding> findings;

    public AnalysisResult() {
    }

    public AnalysisResult(int score, List<CodeFinding> findings) {
        this.score = score;
        this.findings = findings;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<CodeFinding> getFindings() {
        return findings;
    }

    public void setFindings(List<CodeFinding> findings) {
        this.findings = findings;
    }
}