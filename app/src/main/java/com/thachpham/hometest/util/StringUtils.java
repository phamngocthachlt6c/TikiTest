package com.thachpham.hometest.util;

public class StringUtils {
    public static String getKeywordOptimized(String keyword) {
        if (keyword == null || keyword.equals("")) {
            return keyword;
        }
        String result = keyword.trim();
        if (result.contains(" ")) {
            int middleIndex = result.length() / 2;
            for (int i = 0; i < middleIndex; i++) {
                int forwardIndex = middleIndex + i;
                int backIndex = middleIndex - i;
                if (result.charAt(forwardIndex) == ' ') {
                    return result.substring(0, forwardIndex) + "\n" + result.substring(forwardIndex + 1);
                } else if (result.charAt(backIndex) == ' ') {
                    return result.substring(0, backIndex) + "\n" + result.substring(backIndex + 1);
                }
            }
        }
        return result;
    }
}
