package com.ai_summary.backend.util;

public class TextCleaner {
    public static String clean(String text) {
        if (text == null) return "";
        return text
                .replaceAll("<[^>]+>", "")
                .replaceAll("\\s+", " ")
                .replaceAll("[^\\x20-\\x7E]", "")
                .trim();
    }
}
