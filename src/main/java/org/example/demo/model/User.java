package org.example.demo.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 类描述： 用户实体类，映射数据库`user`表
 *
 * @author caofaxin
 * @version 1.0
 * @date 2025/12/24 00:56
 */
@Data  // 自动生成getter/setter/toString等
@NoArgsConstructor  // 自动生成无参构造
@AllArgsConstructor // 自动生成全参构造
@Builder            // 自动生成构建者模式
@TableName("`user`")
public class User {
    // 主键：自增策略（适配MySQL自增主键）
    @TableId(type = IdType.AUTO)
    private Long id;

    // 用户名（若数据库字段是user_name，实体类需写userName，自动映射）
    @TableField("name")  // 显式指定字段名，可选（驼峰匹配时可省略）
    private String name;

    // 年龄
    private Integer age;

    // 邮箱
    private String email;

    // 扩展：若有逻辑删除字段，可添加
    @TableField(exist = false) // 标记逻辑删除字段 非数据库字段（如临时字段）
    private Integer isDeleted;
}