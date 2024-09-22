package com.github.hitzaki.bloom.base;



import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLongArray;

/**
 * 基础位图 支持并发
 * @author hitzaki
 */
public class BaseBitMap {
    private static final int LONG_BITS = 6;
    private final AtomicLongArray data;
    private final AtomicInteger bitCount;
    private final long bitSize;

    public BaseBitMap(long bitNum) {
        if (bitNum < 0){
            bitNum = 64;
        }
        long arrayLen = bitNum >> LONG_BITS;
        arrayLen += (bitNum & 0x3f) > 0 ? 1 : 0;
        bitSize = arrayLen << LONG_BITS;
        data = new AtomicLongArray((int)arrayLen);
        bitCount = new AtomicInteger(0);
    }

    public long getBitSize(){
        return this.bitSize;
    }

    public boolean set(long bitIndex) {
        if (this.get(bitIndex)) {
            return false;
        }
        int longIndex = (int)(bitIndex >>> LONG_BITS);
        long mask = 1L << (int)bitIndex;

        long oldValue;
        long newValue;
        do {
            oldValue = this.data.get(longIndex);
            newValue = oldValue | mask;
            if (oldValue == newValue) {
                return false;
            }
        } while(!this.data.compareAndSet(longIndex, oldValue, newValue));
        this.bitCount.incrementAndGet();
        return true;
    }

    public boolean get(long bitIndex) {
        return (this.data.get((int)(bitIndex >>> LONG_BITS)) & 1L << (int)bitIndex) != 0L;
    }

/*    void putAll(LockArray<Long> other) {

    }

    void putAllByBits(LockArray<Long> other) {

    }*/

    public boolean equals(Object o) {
        if (Objects.isNull(o)){
            return false;
        }
        if (o instanceof BaseBitMap) {
            AtomicLongArray tempBits = ((BaseBitMap) o).data;
            return Arrays.equals(toLongArray(this.data), toLongArray(tempBits));
        }
        return false;
    }

    public int hashCode() {
        return Arrays.hashCode(toLongArray(this.data));
    }

    public static long[] toLongArray(AtomicLongArray atomicLongArray) {
        long[] array = new long[atomicLongArray.length()];
        for(int i = 0; i < array.length; ++i) {
            array[i] = atomicLongArray.get(i);
        }
        return array;
    }
}
