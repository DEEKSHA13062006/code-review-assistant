package com.deeksha.codereview.service;

import org.springframework.stereotype.Service;

@Service
public class CodeAnalyzerService {

    public String analyze(String code) {

        StringBuilder feedback = new StringBuilder();

        // Check empty code
        if (code == null || code.trim().isEmpty()) {
            return "Code is empty.";
        }

        // 1. System.out.println
        if (code.contains("System.out.println")) {
            feedback.append(
                "Avoid using System.out.println for production logging. "
            );
        }

        // 2. Comments
        if (!code.contains("//") && !code.contains("/*")) {
            feedback.append(
                "Consider adding comments for better readability. "
            );
        }

        // 3. Long code
        if (code.length() > 500) {
            feedback.append(
                "Code is lengthy. Consider breaking it into smaller methods. "
            );
        }

        // 4. Hardcoded password detection
        if (code.toLowerCase().contains("password")
                && code.contains("=")) {

            feedback.append(
                "Avoid hardcoding passwords. Use environment variables or secure configuration. "
            );
        }

        // 5. Empty catch block
        if (code.contains("catch") && code.contains("{}")) {
            feedback.append(
                "Empty catch block detected. Handle the exception properly. "
            );
        }

        // 6. TODO / FIXME
        if (code.contains("TODO") || code.contains("FIXME")) {
            feedback.append(
                "TODO or FIXME found. Consider completing the pending implementation. "
            );
        }

        // 7. Very long lines
        String[] lines = code.split("\\r?\\n");

        for (String line : lines) {
            if (line.length() > 120) {
                feedback.append(
                    "Some lines are very long. Consider breaking them into smaller lines. "
                );
                break;
            }
        }

        // 8. Possible null comparison issue
        if (code.contains("== null")) {
            feedback.append(
                "Null checking detected. Make sure null values are handled safely. "
            );
        }

        // No issues
        if (feedback.length() == 0) {
            feedback.append(
                "Code looks clean. No major issues found."
            );
        }

        return feedback.toString();
    }
}