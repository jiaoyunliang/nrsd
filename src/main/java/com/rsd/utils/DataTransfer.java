package com.rsd.utils;

/**
 * 数据传递使用,不允许被继承
 *
 * @author zhangchao
 */
public final class DataTransfer<K, V> {

    private K k;
    private V v;

    public DataTransfer(K k, V v) {
        this.k = k;
        this.v = v;
    }

    public K getK() {
        return k;
    }

    public V getV() {
        return v;
    }
}
