package com.aurora.cache.guava;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池工具
 * @author xzbcode
 */
public class ThreadPoolProvider {

    /**
     * 创建一个线程池
     * @return
     */
    private static ThreadPoolExecutor creatThreadPool() {
        return new ThreadPoolExecutor(2, 6, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
    }

    /**
     * 返回一个监听线程池
     * @return
     */
    public static ListeningExecutorService getExecutorService() {
        return MoreExecutors.listeningDecorator(creatThreadPool());
    }
}
