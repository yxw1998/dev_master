package cn.dev.common.core.domain;

import cn.dev.common.constant.HttpStatus;
import cn.dev.common.enums.RspTypes;

import java.util.HashMap;

/**
 * @author YangXw
 * @date 2023/01/18 14:22
 * @description 消息响应主体
 */
public class AjaxResult extends HashMap<String, Object> {

    /**
     * 状态码
     */
    public static final String CODE = "code";

    /**
     * 返回消息
     */
    public static final String MSG = "msg";

    /**
     * 数据对象
     */
    public static final String DATA = "data";


    /**
     * 初始化一个新创建的 AjaxResult 对象，使其表示一个空消息。
     */
    public AjaxResult() {
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     */
    public AjaxResult(int code, String msg) {
        super.put(CODE, code);
        super.put(MSG, msg);
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     * @param data 数据对象
     */
    public AjaxResult(int code, String msg, Object data) {
        super.put(CODE, code);
        super.put(MSG, msg);
        if (data != null) {
            super.put(DATA, data);
        }
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static AjaxResult success() {
        return AjaxResult.success(RspTypes.SUCCESS.getMessage());
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static AjaxResult success(String msg) {
        return success(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @param data 返回数据
     * @return 成功消息
     */
    public static AjaxResult success(Object data) {
        return success(RspTypes.SUCCESS.getMessage(), data);
    }

    /**
     * 返回成功消息
     *
     * @param msg  返回内容
     * @param data 返回数据
     * @return 成功消息
     */
    public static AjaxResult success(String msg, Object data) {
        return new AjaxResult(RspTypes.SUCCESS.getCode(), msg, data);
    }

    /**
     * 返回警告消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static AjaxResult warn(String msg) {
        return warn(msg, null);
    }

    /**
     * 返回警告消息
     *
     * @param msg  返回内容
     * @param data 返回数据
     * @return 警告消息
     */
    public static AjaxResult warn(String msg, Object data) {
        return new AjaxResult(HttpStatus.WARN, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @return 错误消息
     */
    public static AjaxResult error() {
        return AjaxResult.error("操作失败");
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 错误消息
     */
    public static AjaxResult error(String msg) {
        return AjaxResult.error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 错误消息
     */
    public static AjaxResult error(String msg, Object data) {
        return new AjaxResult(RspTypes.ERROR.getCode(), msg, data);
    }

    /**
     * 返回错误消息
     *
     * @param msg  返回内容
     * @return 错误消息
     */
    public static AjaxResult unauthorized(String msg) {
        return new AjaxResult(RspTypes.UNAUTHORIZED.getCode(), msg);
    }

    /**
     * 返回错误消息
     *
     * @param msg  返回内容
     * @return 错误消息
     */
    public static AjaxResult forbidden(String msg) {
        return new AjaxResult(RspTypes.FORBIDDEN.getCode(), msg);
    }
}
