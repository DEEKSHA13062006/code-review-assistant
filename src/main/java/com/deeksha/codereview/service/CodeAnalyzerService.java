package com.deeksha.codereview.service;

import org.springframework.stereotype.Service;

@Service
public class CodeAnalyzerService {

    public String analyze(String code) {

        StringBuilder feedback = new StringBuilder();

        if (code == null || code.isEmpty()) {
            return "Code is empty.";
        }

        if (code.contains("System.out.println")) {
            feedback.append("Avoid using System.out.println for production logging. ");
        }

        if (!code.contains("//")) {
            feedback.append("Consider adding comments for better readability. ");
        }

        if (code.length() > 500) {
            feedback.append("Code is lengthy. Consider breaking it into smaller methods. ");
        }

        if (feedback.length() == 0) {
            feedback.append("Code looks clean. No major issues found.");
        }

        return feedback.toString();
    }
}