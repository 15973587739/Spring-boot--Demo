package org.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.demo.model.User;

import java.util.List;

/**
 * 接口描述：用户服务接口，继承IService获得更丰富的CRUD
 * @author caofaxin
 * @date 2025/12/24 01:09
 * @version 1.0
 */
public interface UserService extends IService<User> {
    // 可扩展自定义业务方法，比如按年龄查询
    List<User> listByAge(Integer age);
}
