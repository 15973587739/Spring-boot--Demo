package org.example.demo.config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy;
import ch.qos.logback.core.util.FileSize;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;

/**
 * 自定义日志配置类（修复类型不匹配问题，兼容所有Logback版本）
 * @author caofaxin
 * @version 1.0
 * @date 2025/12/24 02:23
 */
@Configuration
public class LogConfig {

    // 日志存储路径
    private static final String LOG_PATH = "logs/app.log";
    // 日志归档路径（%i 用于大小分割的序号）
    private static final String LOG_ARCHIVE_PATH = "logs/archive/app-%d{yyyy-MM-dd}.%i.log";
    // 单个日志文件最大大小
    private static final String MAX_FILE_SIZE = "100MB";
    // 日志保留天数
    private static final int MAX_HISTORY = 30;
    // 日志总归档大小上限
    private static final String TOTAL_SIZE_CAP = "10GB";

    @PostConstruct
    public void initLogConfig() {
        // 获取Logback的LoggerContext（强制转换为具体类型）
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

        // 1. 配置日志编码器（绑定ILoggingEvent类型）
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(loggerContext);
        // 自定义日志格式：时间 | 线程名 | 级别 | 类名 | 内容
        encoder.setPattern("%d{yyyy-MM-dd HH:mm:ss.SSS} | %thread | %-5level | %logger{50} | %msg%n");
        encoder.setCharset(StandardCharsets.UTF_8);
        encoder.start(); // 必须启动编码器

        // 2. 配置滚动策略（绑定ILoggingEvent类型）
        SizeAndTimeBasedRollingPolicy<ILoggingEvent> rollingPolicy = new SizeAndTimeBasedRollingPolicy<>();
        rollingPolicy.setContext(loggerContext);
        // 归档文件路径（包含%i处理大小分割）
        rollingPolicy.setFileNamePattern(LOG_ARCHIVE_PATH);
        // 单个文件最大大小
        rollingPolicy.setMaxFileSize(FileSize.valueOf(MAX_FILE_SIZE));
        // 日志保留天数
        rollingPolicy.setMaxHistory(MAX_HISTORY);
        // 总日志大小上限
        rollingPolicy.setTotalSizeCap(FileSize.valueOf(TOTAL_SIZE_CAP));

        // 3. 配置文件Appender（核心修复：泛型指定为ILoggingEvent）
        RollingFileAppender<ILoggingEvent> fileAppender = new RollingFileAppender<>();
        fileAppender.setContext(loggerContext);
        fileAppender.setName("FILE_APPENDER");
        fileAppender.setFile(LOG_PATH);
        // 绑定编码器（此时类型匹配，不会报错）
        fileAppender.setEncoder(encoder);
        // 绑定滚动策略（先设置Parent再启动）
        rollingPolicy.setParent(fileAppender);
        rollingPolicy.start();
        fileAppender.setRollingPolicy(rollingPolicy);
        // 启动Appender（必须调用，否则日志不会输出）
        fileAppender.start();

        // 4. 配置根日志（核心修复：Logger是ch.qos.logback.classic.Logger类型）
        Logger rootLogger = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME);
        // 移除默认ConsoleAppender（可选，避免日志重复输出）
        rootLogger.detachAppender("CONSOLE");
        // 添加自定义FileAppender（类型匹配，不会报错）
        rootLogger.addAppender(fileAppender);
        // 设置根日志级别
        rootLogger.setLevel(Level.INFO);

        // 5. 自定义包日志级别（示例：com.example包输出DEBUG级日志）
        Logger customLogger = loggerContext.getLogger("com.example");
        customLogger.setLevel(Level.DEBUG);
    }
}