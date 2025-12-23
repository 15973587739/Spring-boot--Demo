package org.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.example.demo.model.User;

/**
 * 接口描述：用户Mapper，继承BaseMapper获得基础CRUD
 *
 * @author caofaxin
 * @version 1.0
 * @date 2025/12/24 01:07
 */
public interface UserMapper extends BaseMapper<User> {
// 无需写任何方法，BaseMapper已包含所有基础CRUD
}
