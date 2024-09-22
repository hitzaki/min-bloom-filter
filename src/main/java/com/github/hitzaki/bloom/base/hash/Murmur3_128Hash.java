package com.github.hitzaki.bloom.base.hash;

import com.github.hitzaki.bloom.base.exception.BaseUtilsException;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * murmur3算法实现 128位
 * @author hitzaki
 */
public class Murmur3_128Hash extends AbstractHash{
    private static final int CHUNK_SIZE = 16;
    private static final long C1 = -8663945395140668459L;
    private static final long C2 = 5545529020109919103L;
    private long h1;
    private long h2;
    private int length;


    Murmur3_128Hash(int seed) {
        super(CHUNK_SIZE);
        this.h1 = (long)seed;
        this.h2 = (long)seed;
        this.length = 0;
    }

    protected void process(ByteBuffer bb) {
        long k1 = bb.getLong();
        long k2 = bb.getLong();
        this.bmix64(k1, k2);
        this.length += 16;
    }

    private void bmix64(long k1, long k2) {
        this.h1 ^= mixK1(k1);
        this.h1 = Long.rotateLeft(this.h1, 27);
        this.h1 += this.h2;
        this.h1 = this.h1 * 5L + 1390208809L;
        this.h2 ^= mixK2(k2);
        this.h2 = Long.rotateLeft(this.h2, 31);
        this.h2 += this.h1;
        this.h2 = this.h2 * 5L + 944331445L;
    }

    @Override
    protected void processRemaining() {
        byteBuffer.flip();

        long k1 = 0L;
        long k2 = 0L;
        this.length += byteBuffer.remaining();
        switch (byteBuffer.remaining()) {
            case 15:
                k2 ^= (long)byteBuffer.get(14) << 48;
            case 14:
                k2 ^= (long)byteBuffer.get(13) << 40;
            case 13:
                k2 ^= (long)byteBuffer.get(12) << 32;
            case 12:
                k2 ^= (long)byteBuffer.get(11) << 24;
            case 11:
                k2 ^= (long)byteBuffer.get(10) << 16;
            case 10:
                k2 ^= (long)byteBuffer.get(9) << 8;
            case 9:
                k2 ^= byteBuffer.get(8);
            case 8:
                k1 ^= byteBuffer.getLong();
                break;
            case 7:
                k1 ^= (long)byteBuffer.get(6) << 48;
            case 6:
                k1 ^= (long)byteBuffer.get(5) << 40;
            case 5:
                k1 ^= (long)byteBuffer.get(4) << 32;
            case 4:
                k1 ^= (long)byteBuffer.get(3) << 24;
            case 3:
                k1 ^= (long)byteBuffer.get(2) << 16;
            case 2:
                k1 ^= (long)byteBuffer.get(1) << 8;
            case 1:
                k1 ^= (long)byteBuffer.get(0);
                break;
            default:
                throw new BaseUtilsException("未知错误:Murmur3 Hash");
        }

        this.h1 ^= mixK1(k1);
        this.h2 ^= mixK2(k2);
    }

    @Override
    public HashCode makeHash() {
        this.h1 ^= (long)this.length;
        this.h2 ^= (long)this.length;
        this.h1 += this.h2;
        this.h2 += this.h1;
        this.h1 = fmix64(this.h1);
        this.h2 = fmix64(this.h2);
        this.h1 += this.h2;
        this.h2 += this.h1;
        return new HashCode(ByteBuffer.wrap(new byte[16])
                .order(ByteOrder.LITTLE_ENDIAN).putLong(this.h1).putLong(this.h2).array());
    }

    private static long fmix64(long k) {
        k ^= k >>> 33;
        k *= -49064778989728563L;
        k ^= k >>> 33;
        k *= -4265267296055464877L;
        k ^= k >>> 33;
        return k;
    }

    private static long mixK1(long k1) {
        k1 *= -8663945395140668459L;
        k1 = Long.rotateLeft(k1, 31);
        k1 *= 5545529020109919103L;
        return k1;
    }

    private static long mixK2(long k2) {
        k2 *= 5545529020109919103L;
        k2 = Long.rotateLeft(k2, 33);
        k2 *= -8663945395140668459L;
        return k2;
    }
}
