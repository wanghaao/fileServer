package fileserver.utils;

import fileserver.entity.FileEntity;

import java.util.HashMap;
import java.util.Set;

/*
模拟实现一个 hash 的array ，用于存储file节点
 */
public class FileEntityHashArray<K,V> extends HashMap<K,V> implements HashArrayOperation {

    // 数组存放的是  文件实体类 ,用的是  泛型，实际是  fileEntity
    private transient FileEntity[] array;
    private volatile int counter = 0;
    static final int DEFAULT_SIZE = 128;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    static final int hash(Object key) {
        return (key == null) ? 0 : (key.hashCode()) ^ (key.hashCode() >>> 16);
    }

    static final int determineIndex(int hashcode,int size){
        return (size-1) & hashcode;
    }
    @Override
    public V get(Object key) {
        return (V) array[determineIndex(hash(key),DEFAULT_SIZE)];
    }

    @Override
    public V put(K key, V value) {
        if(array==null){
            array = new FileEntity[DEFAULT_SIZE];
        }

        int index = determineIndex(hash(key),DEFAULT_SIZE);
        if(array[index]==null){
            array[index] = (FileEntity) value;
            counter++;// 真实的填上了空格
        }else{
            array[index] = (FileEntity) value;
        }
        return value;
    }

    public boolean whetherFull(){
        if(counter==DEFAULT_SIZE){
            return true;
        }
        return false;
    }

    // 是否存在指定文件名称
    public boolean whetherExistKey(String key){
        int index = (DEFAULT_SIZE-1)&hash(key);
        return (array[index]!=null) && array[index].getFileName().equals(key);
    }

    @Override
    public Set<String> arrayKeySet() {
        return (Set<String>) super.keySet();
    }

    @Override
    public int getArrayCapacity() {
         return DEFAULT_SIZE;
    }

    @Override
    public int getElementNumber() {
        return counter;
    }

    @Override
    public Set<Object> arrayValueSet() {
        return (Set<Object>) super.values();
    }
}
