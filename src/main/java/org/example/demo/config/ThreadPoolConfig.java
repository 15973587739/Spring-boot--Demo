package org.example.demo.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 类描述：线程池配置类（支持@Async注解）
 *
 * @author caofaxin
 * @version 1.0
 * @date 2025/12/24 02:23
 */
@Configuration
@EnableAsync // 开启异步注解支持
public class ThreadPoolConfig {

    /**
     * 核心线程数（CPU核心数+1）
     */
    private static final int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors() + 1;
    /**
     * 最大线程数
     */
    private static final int MAX_POOL_SIZE = CORE_POOL_SIZE * 2;
    /**
     * 队列容量
     */
    private static final int QUEUE_CAPACITY = 100;
    /**
     * 空闲线程存活时间（秒）
     */
    private static final int KEEP_ALIVE_SECONDS = 60;

    /**
     * 自定义异步线程池
     */
    @Bean(name = "asyncTaskExecutor")
    public Executor asyncTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数
        executor.setCorePoolSize(CORE_POOL_SIZE);
        // 最大线程数
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        // 队列容量
        executor.setQueueCapacity(QUEUE_CAPACITY);
        // 空闲线程存活时间
        executor.setKeepAliveSeconds(KEEP_ALIVE_SECONDS);
        // 线程名前缀（便于日志排查）
        executor.setThreadNamePrefix("async-task-");
        // 拒绝策略：核心线程忙、队列满时，使用调用者线程执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化线程池
        executor.initialize();
        return executor;
    }
}