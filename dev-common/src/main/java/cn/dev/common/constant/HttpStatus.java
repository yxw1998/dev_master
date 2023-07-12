package cn.dev.common.constant;

/**
 * @author YangXw
 * @date 2023/01/18 12:39
 * @description 返回状态码
 */
public class HttpStatus {

    /**
     * 操作成功
     */
    public static final int SUCCESS = 200;

    /**
     * 对象创建/修改成功
     */
    public static final int CREATED = 201;

    /**
     * 请求已经被接受
     */
    public static final int ACCEPTED = 202;

    /**
     * 操作已经执行成功，但是没有返回数据
     */
    public static final int NO_CONTENT = 204;

    /**
     * 参数列表错误（缺少，格式不匹配）
     */
    public static final int BAD_REQUEST = 400;

    /**
     * 未授权
     */
    public static final int UNAUTHORIZED = 401;

    /**
     * 访问受限，授权过期
     */
    public static final int FORBIDDEN = 403;

    /**
     * 资源，服务未找到
     */
    public static final int NOT_FOUND = 404;

    /**
     * 不允许的http方法
     */
    public static final int BAD_METHOD = 405;

    /**
     * 系统内部错误
     */
    public static final int ERROR = 500;

    /**
     * 系统警告消息
     */
    public static final int WARN = 601;
}
