package org.example.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.demo.model.User;
import org.example.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类描述：用户RESTful接口  * 基础路径：/users
 *
 * @author caofaxin
 * @version 1.0
 * @date 2025/12/24 01:11
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 1. 新增用户
     * 请求方式：POST
     * 请求体：JSON格式的User对象（无需传id，自增）
     * 示例：{"name":"张三","age":20,"email":"zhangsan@example.com"}
     */
    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        boolean save = userService.save(user);
        if (save) {
            // 新增成功，返回201 Created + 用户对象（含自增id）
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
        // 新增失败，返回400 Bad Request
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * 2. 根据ID查询用户
     * 请求方式：GET
     * 请求路径：/api/users/1
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getById(id);
        if (user != null) {
            // 查询成功，返回200 OK + 用户对象
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        // 无数据，返回404 Not Found
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * 3. 分页查询所有用户
     * 请求方式：GET
     * 请求参数：pageNum（页码，默认1）、pageSize（每页条数，默认10）
     * 示例：/api/users?pageNum=1&pageSize=10
     */
    @GetMapping
    public ResponseEntity<IPage<User>> listUsers(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        // 构建分页对象
        Page<User> page = new Page<>(pageNum, pageSize);
        // 分页查询
        IPage<User> userPage = userService.page(page);
        // 返回200 OK + 分页数据
        return new ResponseEntity<>(userPage, HttpStatus.OK);
    }

    /**
     * 4. 按年龄查询用户
     * 请求方式：GET
     * 请求路径：/api/users/age/20
     */
    @GetMapping("/age/{age}")
    public ResponseEntity<List<User>> listUsersByAge(@PathVariable Integer age) {
        List<User> userList = userService.listByAge(age);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    /**
     * 5. 更新用户
     * 请求方式：PUT
     * 请求路径：/api/users/1
     * 请求体：JSON格式的User对象（需传id）
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable Long id, @RequestBody User user) {
        // 确保更新的是指定ID的用户
        user.setId(id);
        boolean update = userService.updateById(user);
        if (update) {
            // 更新成功，返回204 No Content
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        // 更新失败，返回400 Bad Request
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * 6. 删除用户
     * 请求方式：DELETE
     * 请求路径：/api/users/1
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean remove = userService.removeById(id);
        if (remove) {
            // 删除成功，返回204 No Content
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        // 删除失败，返回400 Bad Request
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}