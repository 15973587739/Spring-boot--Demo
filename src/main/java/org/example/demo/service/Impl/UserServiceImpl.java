package org.example.demo.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.demo.mapper.UserMapper;
import org.example.demo.model.User;
import org.example.demo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 类描述：用户服务实现类
 *
 * @author caofaxin
 * @version 1.0
 * @date 2025/12/24 01:10
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 自定义方法：按年龄查询用户
     */
    @Override
    public List<User> listByAge(Integer age) {
        // 1. 创建Lambda条件构造器（针对User实体）
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        // 2. 添加“age等于指定值”的条件
        wrapper.eq(User::getAge, age); // WHERE age = ?
        // 3. 执行查询（最终SQL：SELECT id,name,age,email FROM `user` WHERE age = ?）
        List<User> userList = baseMapper.selectList(wrapper);
        return baseMapper.selectList(wrapper);
    }
    /**
     * 分页查询用户
     * @param pageNum 页码（从1开始）
     * @param pageSize 每页条数
     * @return 分页结果（包含总条数、总页数、当前页数据）
     */
    @Override
    public IPage<User> pageUser(Integer pageNum, Integer pageSize) {
        // 1. 创建Page对象（指定页码和每页条数）
        Page<User> page = new Page<>(pageNum, pageSize);
        // 2. 执行分页查询（MyBatis-Plus自动拼接LIMIT语句）
        // 底层SQL：SELECT id,name,age,email FROM `user` LIMIT (pageNum-1)*pageSize, pageSize
        IPage<User> userPage = baseMapper.selectPage(page, null);
        return userPage;
    }

    /**
     * 带条件的分页查询（比如按年龄查询）
     */
    @Override
    public IPage<User> pageUserByAge(Integer pageNum, Integer pageSize, Integer age) {
        Page<User> page = new Page<>(pageNum, pageSize);
        // 拼接条件：WHERE age = ?
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getAge, age);
        // 带条件分页查询
        IPage<User> userPage = baseMapper.selectPage(page, wrapper);
        return userPage;
    }
}