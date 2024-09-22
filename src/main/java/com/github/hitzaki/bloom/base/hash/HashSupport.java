package com.github.hitzaki.bloom.base.hash;
/**
 * 哈希支持, 可以通过实现此接口实现自己的哈希支持
 * @author hitzaki
 */
public interface HashSupport<T> {
    void put(AbstractHash hash, T instance);
}
