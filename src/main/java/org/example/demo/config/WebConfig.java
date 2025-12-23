package org.example.demo.config;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 类描述：Web通用配置类（日期格式化、静态资源、消息转换器）
 *
 * @author caofaxin
 * @version 1.0
 * @date 2025/12/24 02:20
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 日期格式化常量
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 配置JSON序列化/反序列化规则（统一LocalDateTime格式）
     */
    /**
     * 修复方案：通过自定义Bean替换默认的MappingJackson2HttpMessageConverter
     * 替代直接操作converters列表，兼容所有版本
     */
    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();

        // 忽略JSON字符串中不存在的属性
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // 配置LocalDate/LocalDateTime序列化和反序列化规则
        SimpleModule module = new SimpleModule();
        // 序列化：Java对象 -> JSON字符串
        module.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DATE_FORMAT)));
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
        // 反序列化：JSON字符串 -> Java对象
        module.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DATE_FORMAT)));
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));

        objectMapper.registerModule(module);
        converter.setObjectMapper(objectMapper);

        return converter;
    }

    /**
     * 配置静态资源映射（如上传的文件、前端静态资源）
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 本地文件上传路径映射（示例：访问http://localhost:8080/upload/xxx.jpg 映射到本地D:/upload/xxx.jpg）
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:D:/upload/") // 本地绝对路径
                .setCachePeriod(3600); // 缓存时间（秒）

        // 前端静态资源映射（如Vue/React打包后的dist目录）
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(3600);
    }
}