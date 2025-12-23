package org.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
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
    /**
     * 分页查询用户
     * @param pageNum 页码（从1开始）
     * @param pageSize 每页条数
     * @return 分页结果（包含总条数、总页数、当前页数据）
     */
    IPage<User> pageUser(Integer pageNum, Integer pageSize);
    /**
     * 带条件的分页查询（比如按年龄查询）
     */
    IPage<User> pageUserByAge(Integer pageNum, Integer pageSize, Integer age);
}
