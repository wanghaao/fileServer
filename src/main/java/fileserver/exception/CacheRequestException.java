package fileserver.exception;

/**
 * @description: 缓存文件请求的异常
 * @author: wang hao
 */
public class CacheRequestException extends RuntimeException {
    public CacheRequestException(String message) {
        super(message);
    }

    public CacheRequestException() {
        super();
    }

}
