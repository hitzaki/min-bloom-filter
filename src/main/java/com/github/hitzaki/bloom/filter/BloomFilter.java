package com.github.hitzaki.bloom.filter;

import com.github.hitzaki.bloom.base.BaseBitMap;
import com.github.hitzaki.bloom.base.hash.HashSupport;
import com.github.hitzaki.bloom.validation.Validations;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * 布隆过滤器
 * @author hitzaki
 */
public class BloomFilter<T> {

    private final BaseBitMap baseBitMap;
    private final int numHashFunctions;
    private final HashSupport<? super T> hashSupport;
    private final HashStrategy hashStrategy;

    public BloomFilter(long numBits, int numHashFunctions, HashSupport<? super T> hashSupport, HashStrategy hashStrategy) {
        this.baseBitMap = new BaseBitMap(numBits);
        this.numHashFunctions = numHashFunctions;
        this.hashSupport = Validations.checkNotNull(hashSupport);
        this.hashStrategy = hashStrategy;
    }

    public boolean put(T object) {
        return hashStrategy.put(object, hashSupport, numHashFunctions, baseBitMap);
    }

    public boolean mightContain(T object) {
        return hashStrategy.mightContain(object, hashSupport, numHashFunctions, baseBitMap);
    }


    @Override
    public boolean equals(@Nullable Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof BloomFilter) {
            BloomFilter<?> other = (BloomFilter<?>) object;
            return this.numHashFunctions == other.numHashFunctions
                    && this.hashSupport.equals(other.hashSupport)
                    && this.baseBitMap.equals(other.baseBitMap)
                    && this.hashStrategy.equals(other.hashStrategy);
        }
        return false;
    }

    public static <T> BloomFilter<T> create(HashSupport<? super T> hashSupport, long expectedInsertions, double fpp){
        return create(hashSupport, expectedInsertions, fpp, HashStrategyEnum.Murmur3_128_STRATEGY);
    }

    public static <T> BloomFilter<T> create(HashSupport<? super T> hashSupport, long expectedInsertions, double fpp, HashStrategy hashStrategy){
        Validations.checkNotNull(hashStrategy);
        if (expectedInsertions <= 0) {
            expectedInsertions = 1;
        }
        long numBits = optimalNumOfBits(expectedInsertions, fpp);
        int numHashFunctions = optimalNumOfHashFunctions(expectedInsertions, numBits);
        return new BloomFilter<T>(numBits, numHashFunctions, hashSupport, hashStrategy);
    }

    /**
     * 位图长度
     */
    public static long optimalNumOfBits(long n, double p) {
        // 初始值
        if (n == 0) {
            n = 1;
        }
        if (p == 0.0) {
            p = Double.MIN_VALUE;
        }

        return (long)((double)(-n) * Math.log(p) / (Math.log(2.0) * Math.log(2.0)));
    }

    /**
     * 哈希函数数量
     */
    public static int optimalNumOfHashFunctions(long n, long m) {
        return Math.max(1, (int)Math.round((double)m / (double)n * Math.log(2.0)));
    }
}
