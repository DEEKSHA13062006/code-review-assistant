package com.deeksha.codereview.service;

import com.deeksha.codereview.dto.AnalysisResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CodeAnalyzerServiceTest {

    private final CodeAnalyzerService analyzer = new CodeAnalyzerService();

    @Test
    void shouldDetectSystemOutPrintln() {

        String code =
                "public class Test { " +
                "public static void main(String[] args) { " +
                "System.out.println(\"Hello\"); " +
                "} }";

        AnalysisResult result = analyzer.analyze(code);

        assertTrue(result.getScore() < 100);

        assertTrue(result.getFindings().stream()
                .anyMatch(finding ->
                        finding.getMessage()
                                .contains("System.out.println")));
    }

    @Test
    void shouldDetectHardcodedPassword() {

        String code =
                "String password = \"secret123\";";

        AnalysisResult result = analyzer.analyze(code);

        assertTrue(result.getFindings().stream()
                .anyMatch(finding ->
                        finding.getSeverity().equals("HIGH")
                                && finding.getCategory().equals("SECURITY")));
    }

    @Test
    void shouldReturnCleanScoreForCleanCode() {

        String code =
                "public class Test {\n" +
                "    public void hello() {\n" +
                "        int x = 10;\n" +
                "    }\n" +
                "}";

        AnalysisResult result = analyzer.analyze(code);

        assertEquals(100, result.getScore());
        assertTrue(result.getFindings().isEmpty());
    }
}