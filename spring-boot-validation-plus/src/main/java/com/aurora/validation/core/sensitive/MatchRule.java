package com.aurora.validation.core.sensitive;

/**
 * 匹配规则
 * @author xzbcode
 */
public enum MatchRule {

    /**最小匹配规则*/
    MIN_DEPTH(1),

    /**最大匹配规则*/
    MAX_DEPTH(2);

    MatchRule(Integer type) {
        this.type = type;
    }

    public Integer type;

    public Integer getType() {
        return type;
    }

}
