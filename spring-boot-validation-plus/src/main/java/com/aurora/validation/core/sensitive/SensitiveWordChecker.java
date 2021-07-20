package com.aurora.validation.core.sensitive;

import java.util.Iterator;
import java.util.Set;

/**
 * 敏感词校验器
 * @author xzbcode
 */
public class SensitiveWordChecker {

    /**
     * 判断文字是否包含敏感字符
     * @param content 检测内容
     * @param matchRule 匹配规则
     * @param dataSource 敏感词数据源
     * @return 若包含返回【true】，否则返回【false】
     */
    public static boolean contain(String content, MatchRule matchRule, ISensitiveWordDataSource dataSource) {
        boolean flag = false;
        for(int i = 0 ; i < content.length() ; i++){
            int matchFlag = SensitiveWordDataResolver.check(content, i, matchRule, dataSource.getData());
            // 判断是否包含敏感字符
            if(matchFlag > 0){
                // 大于0存在，返回true
                flag = true;
            }
        }
        return flag;
    }


    /**
     * 替换敏感字字符
     * @param content 检测内容
     * @param matchRule 匹配规则 
     * @param dataSource 敏感词库
     * @param replaceChar 替换字符，默认【*】
     */
    public static String replace(String content, MatchRule matchRule, String replaceChar, ISensitiveWordDataSource dataSource) {
        String resultTxt = content;
        // 获取所有的敏感词
        Set<String> set = SensitiveWordDataResolver.get(content, matchRule, dataSource.getData());
        Iterator<String> iterator = set.iterator();
        String word = null;
        String replaceString = null;
        // 如果替换字符为null，默认成 *
        replaceChar = replaceChar==null?"*":replaceChar;
        while (iterator.hasNext()) {
            word = iterator.next();
            replaceString = SensitiveWordDataResolver.getReplaceChars(replaceChar, word.length());
            resultTxt = resultTxt.replaceAll(word, replaceString);
        }
        // 返回替换后的文本
        return resultTxt;
    }

}
