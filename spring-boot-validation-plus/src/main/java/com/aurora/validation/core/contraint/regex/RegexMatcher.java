package com.aurora.validation.core.contraint.regex;

import java.util.regex.Pattern;

/**
 * 正则表达式匹配工具类
 * @author xzbcode
 */
public class RegexMatcher {

    /**
     * 匹配一个正则
     * @param pattern 正则
     * @param content 匹配内容
     * @return
     */
    public static boolean isMatch(Pattern pattern, CharSequence content) {
        return content != null && pattern != null ? pattern.matcher(content).matches() : false;
    }

    /**
     * 匹配一个正则表达式
     * @param pattern 正则表达式
     * @param content 匹配内容
     * @return
     */
    public static boolean isMatch(String pattern, CharSequence content) {
        return content != null && pattern != null ? Pattern.compile(pattern).matcher(content).matches() : false;
    }

}
