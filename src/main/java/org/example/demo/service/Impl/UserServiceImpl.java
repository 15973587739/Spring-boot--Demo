package org.example.demo.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getAge, age); // WHERE age = ?
        return baseMapper.selectList(wrapper);
    }
}