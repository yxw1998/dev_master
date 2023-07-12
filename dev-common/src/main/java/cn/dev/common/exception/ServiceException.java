package cn.dev.common.exception;

/**
 * @author YangXw
 * @date 2023/01/25 23:25
 * @description
 */
public class ServiceException extends RuntimeException{

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }
}
