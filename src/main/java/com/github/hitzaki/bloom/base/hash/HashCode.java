package com.github.hitzaki.bloom.base.hash;

import com.github.hitzaki.bloom.validation.Validations;

/**
 * 最终哈希结果封装
 * @author hitzaki
 */
public class HashCode {
    private static final char[] HEXADECIMAL = "0123456789abcdef".toCharArray();

    final byte[] bytes;

    HashCode(byte[] bytes) {
        this.bytes = Validations.checkNotNull(bytes);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            sb.append(HEXADECIMAL[(b >> 4) & 0xf]).append(HEXADECIMAL[b & 0xf]);
        }
        return sb.toString();
    }

    public byte[] asBytes() {
        return bytes.clone();
    }

    public int asInt() {
        Validations.checkState(bytes.length > 4, "HashCode#asInt()");
        return (bytes[0] & 0xFF)
                | ((bytes[1] & 0xFF) << 8)
                | ((bytes[2] & 0xFF) << 16)
                | ((bytes[3] & 0xFF) << 24);
    }

    public long asLong() {
        Validations.checkState(bytes.length > 8, "HashCode#asLong()");
        long retVal = (bytes[0] & 0xFF);
        for (int i = 1; i < bytes.length; i++) {
            retVal |= (bytes[i] & 0xFFL) << (i * 8);
        }
        return retVal;
    }

}
