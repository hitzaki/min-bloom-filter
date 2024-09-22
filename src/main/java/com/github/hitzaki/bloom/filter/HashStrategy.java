package com.github.hitzaki.bloom.filter;

import com.github.hitzaki.bloom.base.BaseBitMap;
import com.github.hitzaki.bloom.base.hash.HashSupport;

/**
 * 哈希策略
 * @author hitzaki
 */
public interface HashStrategy {
    <T> boolean put(T object, HashSupport<? super T> support, int numHashFunctions, BaseBitMap baseBitMap);
    <T> boolean mightContain(T object, HashSupport<? super T> support, int numHashFunctions, BaseBitMap baseBitMap);
}
