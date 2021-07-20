package com.aurora.validation.core.sensitive;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 敏感词解析器
 * @author xzbcode
 */
class SensitiveWordDataResolver {

    /**
     * 检查文字中是否包含敏感字符
     * @param content 检测内容
     * @param startIndex 开始检测下标
     * @param matchRule 匹配规则
     * @param sensitiveData 敏感词库
     * @return 如果存在，则返回敏感词字符的长度，不存在返回0
     */
    static int check(String content, int startIndex, MatchRule matchRule, Map<String, String> sensitiveData) {
        // 敏感词结束标识位：用于敏感词只有1位的情况
        boolean flag = false;
        // 匹配标识数默认为0
        int matchFlag = 0;
        char word = 0;
        // 如果用户没有自己加装敏感字符，则默认
        Map wordMap = sensitiveData;
        for(int i = startIndex; i < content.length() ; i++){
            word = content.charAt(i);
            // 获取指定key
            wordMap = (Map) wordMap.get(word);
            if(wordMap != null){
                // 存在，则判断是否为最后一个，找到相应key，匹配标识+1
                matchFlag ++;
                // 如果为最后一个匹配规则,结束循环，返回匹配标识数
                if("1".equals(wordMap.get("isEnd"))) {
                    // 结束标志位为true
                    flag = true;
                    // 最小规则，直接返回,最大规则还需继续查找
                    if(1 == matchRule.getType()) {
                        break;
                    }
                }
            } else {
                // 不存在，直接返回
                break;
            }
        }
        if(!flag) {
            matchFlag = 0;
        }
        return matchFlag;
    }

    /**
     * 获取文字中的敏感词集
     * @param txt 文字
     * @param matchRule 匹配规则
     * @param sensitiveData 敏感词库
     * @return Set<String>
     */
    static Set<String> get(String txt, MatchRule matchRule, Map<String, String> sensitiveData){
        Set<String> sensitiveWordList = new HashSet<String>();
        for(int i = 0 ; i < txt.length() ; i++){
            int length = check(txt, i, matchRule, sensitiveData);
            // 判断是否包含敏感字符
            if(length > 0){
                // 存在,加入list中
                sensitiveWordList.add(txt.substring(i, i+length));
                // 减1的原因，是因为for会自增
                i = i + length - 1;
            }
        }
        // 返回文字中的敏感词
        return sensitiveWordList;
    }


    /**
     * 获取替换字符串
     * @param replaceChar 被替换的字符串
     * @param length 长度
     * @return String
     */
    static String getReplaceChars(String replaceChar, int length){
        String resultReplace = replaceChar;
        for(int i = 1 ; i < length ; i++){
            resultReplace += replaceChar;
        }
        return resultReplace;
    }

}
