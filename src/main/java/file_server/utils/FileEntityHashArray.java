package file_server.utils;

import file_server.entity.FileEntity;

import java.util.HashMap;
import java.util.Set;

/*
模拟实现一个 hash 的array ，用于存储file节点
 */
public class FileEntityHashArray<K,V> extends HashMap<K,V> implements HashArrayOperation {

    // 数组存放的是  文件实体类 ,用的是  泛型，实际是  fileEntity
    private FileEntity[] array;
    private volatile int counter = 0;
    static final int defaultSize = 128;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }


    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    static final int determineIndex(int hashcode,int size){
        return (size-1) & hashcode;
    }
    @Override
    public V get(Object key) {
        return (V) array[determineIndex(hash(key),defaultSize)];
    }

    @Override
    public V put(K key, V value) {
        if(array==null){
            array = new FileEntity[defaultSize];
        }

        int index = determineIndex(hash(key),defaultSize);
        if(array[index]==null){
            array[index] = (FileEntity) value;
            counter++;// 真实的填上了空格
        }else{
            FileEntity fileEntity = array[index];
            array[index] = (FileEntity) value;
        }
        return value;
    }

    public boolean whetherFull(){
        return counter == defaultSize ? true:false;
    }

    // 是否存在指定文件名称
    public boolean whetherExistKey(String key){
        int index = (defaultSize-1)&hash(key);
        return (array[index]!=null) && array[index].getFileName().equals(key);
    }

    @Override
    public Set<String> arrayKeySet() {
        return null;
    }

    @Override
    public int getArrayCapacity() {
         return defaultSize;
    }

    @Override
    public int getElementNumber() {
        return counter;
    }

    @Override
    public Set<Object> arrayValueSet() {
        return null;
    }
}
