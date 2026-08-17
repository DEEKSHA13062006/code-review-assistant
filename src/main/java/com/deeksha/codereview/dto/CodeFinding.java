package com.deeksha.codereview.dto;

public class CodeFinding {

    private String severity;
    private String category;
    private String message;

    public CodeFinding() {
    }

    public CodeFinding(String severity, String category, String message) {
        this.severity = severity;
        this.category = category;
        this.message = message;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}