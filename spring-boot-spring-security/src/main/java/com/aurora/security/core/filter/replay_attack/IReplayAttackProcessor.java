package com.aurora.security.core.filter.replay_attack;

import javax.servlet.http.HttpServletRequest;

/**
 * 防重放处理器接口
 * @author xzbcode
 */
public interface IReplayAttackProcessor {

    boolean process(HttpServletRequest request) throws ReplayAttackException;

}
