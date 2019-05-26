package file_server.utils;

import java.util.Set;

/**
 * @description: hashArray 的 相关操作
 * @author: wang hao
 */
public interface HashArrayOperation {


    Set<String> arrayKeySet();
    int getArrayCapacity();
    int getElementNumber();

    Set<Object> arrayValueSet();
}
