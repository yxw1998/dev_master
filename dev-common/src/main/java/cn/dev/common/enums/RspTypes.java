package cn.dev.common.enums;

import cn.dev.common.constant.HttpStatus;

/**
 * @author YangXw
 * @description 返回消息类型枚举类
 */
public enum RspTypes {

    /**
     * SUCCESS: 服务器成功返回用户请求的数据
     */
    SUCCESS(HttpStatus.SUCCESS, "请求成功！"),

    /**
     * CREATED: 对象新建/修改数据成功
     */
    CREATED(HttpStatus.CREATED, "创建成功！"),

    /**
     * ACCEPTED: 表示一个请求已经进入后台排队（异步任务）
     */
    ACCEPTED(HttpStatus.ACCEPTED, "请求已被接受！"),

    /**
     * NO_CONTENT: 操作已经执行成功，但是没有返回数据
     */
    NO_CONTENT(HttpStatus.NO_CONTENT, "操作成功！"),

    /**
     * BAD_REQUEST: 参数列表错误（缺少，格式不匹配）
     */
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "参数列表错误（缺少，格式不匹配）！"),

    /**
     * UNAUTHORIZED-[*]: 用户没有权限（令牌、用户名、密码错误）
     */
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "未认证！"),

    /**
     * FORBIDDEN-[*]: 用户认证通过，访问受限，授权过期
     */
    FORBIDDEN(HttpStatus.FORBIDDEN, "访问受限，授权过期！"),

    /**
     * NOT_FOUND-[*]: 请求的资源不存在
     */
    NOT_FOUND(HttpStatus.NOT_FOUND, "资源，服务未找到！"),

    /**
     * UNPROCESSABLE_ENTITY-[POST/PUT/DELETE]: 操作失败
     */
    ERROR(HttpStatus.ERROR, "操作失败！");

    private final Integer code;

    private final String message;

    RspTypes(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
