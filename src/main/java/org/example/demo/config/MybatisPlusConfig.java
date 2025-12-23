package org.example.demo.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 类描述：添加分页插件
 *
 * @author caofaxin
 * @version 1.0
 * @date 2025/12/24 01:44
 */
// 1. 标记这是Spring的配置类，会被Spring扫描并加载其中的@Bean方法
@Configuration
// 2. 扫描MyBatis-Plus的Mapper接口所在包，替代每个Mapper上的@Mapper注解
// 额外扫描Mapper接口（如果Mapper不在启动类子包下，需要手动指定）
@MapperScan("org.example.demo.mapper")
public class MybatisPlusConfig {

    /**
     * 添加分页插件
     */
    // 3. 将方法返回的对象注册为Spring容器中的Bean（供MyBatis-Plus使用）
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        // 4. 创建MyBatis-Plus的拦截器总容器（可添加多个拦截器：分页、乐观锁、防全表更新等）
        // 添加MySQL分页拦截器，适配你的数据库类型（你用的是MySQL）
        PaginationInnerInterceptor paginationInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
        // 可选配置：设置分页溢出后是否返回最后一页（默认false，溢出报错；true则返回最后一页）
        paginationInterceptor.setOverflow(true);
        // 可选配置：设置单页最大条数（默认500，超出会报错，可根据需求调整）
        paginationInterceptor.setMaxLimit(1000L);
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 5. 添加MySQL分页拦截器（指定DbType.MYSQL适配MySQL分页语法）
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        // 注释说明：如果配置多个插件，分页插件建议最后添加；多数据源可省略DbType
        // 如果有多数据源可以不配具体类型, 否则都建议配上具体的 DbType
        return interceptor;
    }
}
