package org.example.demo.util;

import lombok.Data;

/**
 * 类描述：全局统一响应结果工具类
 *
 * @author caofaxin
 * @version 1.0
 * @date 2025/12/24 02:12
 */
@Data
public class Result<T> {
    /**
     * 响应状态码
     */
    private int code;
    /**
     * 响应消息
     */
    private String msg;
    /**
     * 响应数据
     */
    private T data;

    // 私有构造方法，禁止外部直接创建
    private Result() {}

    // 成功响应（无数据）
    public static <T> Result<T> success() {
        return success(null);
    }

    // 成功响应（带数据）
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMsg("操作成功");
        result.setData(data);
        return result;
    }

    // 失败响应（自定义消息）
    public static <T> Result<T> fail(String msg) {
        Result<T> result = new Result<>();
        result.setCode(500);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    // 失败响应（自定义状态码和消息）
    public static <T> Result<T> fail(int code, String msg) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }
}