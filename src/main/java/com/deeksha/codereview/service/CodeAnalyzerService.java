package com.deeksha.codereview.service;

import com.deeksha.codereview.dto.AnalysisResult;
import com.deeksha.codereview.dto.CodeFinding;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CodeAnalyzerService {

    public AnalysisResult analyze(String code) {

        List<CodeFinding> findings = new ArrayList<>();

        // Check empty code
        if (code == null || code.trim().isEmpty()) {

            findings.add(new CodeFinding(
                    "HIGH",
                    "CODE_QUALITY",
                    "Code is empty."
            ));

            return new AnalysisResult(0, findings);
        }

        // 1. Division by zero detection
        if (code.matches("(?s).*\\b[A-Za-z0-9_$]+\\s*/\\s*0\\b.*")) {

            findings.add(new CodeFinding(
                    "CRITICAL",
                    "BUG",
                    "Division by zero detected. This can cause an ArithmeticException at runtime."
            ));
        }

        // 2. System.out.println
        if (code.contains("System.out.println")) {

            findings.add(new CodeFinding(
                    "LOW",
                    "CODE_QUALITY",
                    "Avoid using System.out.println for production logging."
            ));
        }

        // 3. Comments
        if (!code.contains("//")
                && !code.contains("/*")
                && code.length() > 100) {

            findings.add(new CodeFinding(
                    "LOW",
                    "MAINTAINABILITY",
                    "Consider adding comments for better readability."
            ));
        }

        // 4. Long code
        if (code.length() > 500) {

            findings.add(new CodeFinding(
                    "MEDIUM",
                    "MAINTAINABILITY",
                    "Code is lengthy. Consider breaking it into smaller methods."
            ));
        }

        // 5. Hardcoded password detection
        String lowerCode = code.toLowerCase();

        if ((lowerCode.contains("password")
                || lowerCode.contains("passwd")
                || lowerCode.contains("pwd"))
                && (code.contains("=")
                || code.contains(":"))) {

            findings.add(new CodeFinding(
                    "HIGH",
                    "SECURITY",
                    "Avoid hardcoding passwords. Use environment variables or secure configuration."
            ));
        }

        // 6. Empty catch block
        if (code.matches("(?s).*catch\\s*\\([^)]*\\)\\s*\\{\\s*\\}.*")) {

            findings.add(new CodeFinding(
                    "HIGH",
                    "ERROR_HANDLING",
                    "Empty catch block detected. Handle the exception properly."
            ));
        }

        // 7. TODO / FIXME
        if (code.contains("TODO") || code.contains("FIXME")) {

            findings.add(new CodeFinding(
                    "LOW",
                    "BEST_PRACTICE",
                    "TODO or FIXME found. Consider completing the pending implementation."
            ));
        }

        // 8. Very long lines
        String[] lines = code.split("\\r?\\n");

        for (String line : lines) {

            if (line.length() > 120) {

                findings.add(new CodeFinding(
                        "LOW",
                        "CODE_QUALITY",
                        "Some lines are very long. Consider breaking them into smaller lines."
                ));

                break;
            }
        }

        // 9. Null comparison
        if (code.contains("== null")) {

            findings.add(new CodeFinding(
                    "INFO",
                    "BEST_PRACTICE",
                    "Null checking detected. Make sure null values are handled safely."
            ));
        }

        // Calculate score
        int score = 100;

        for (CodeFinding finding : findings) {

            switch (finding.getSeverity()) {

                case "CRITICAL":
                    score -= 40;
                    break;

                case "HIGH":
                    score -= 20;
                    break;

                case "MEDIUM":
                    score -= 10;
                    break;

                case "LOW":
                    score -= 5;
                    break;

                case "INFO":
                    break;

                default:
                    break;
            }
        }

        // Keep score between 0 and 100
        score = Math.max(0, Math.min(100, score));

        return new AnalysisResult(score, findings);
    }
}