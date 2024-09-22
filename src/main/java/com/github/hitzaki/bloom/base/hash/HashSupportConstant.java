package com.github.hitzaki.bloom.base.hash;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * 系统内置的哈希支持类型
 * @author hitzaki
 */
public class HashSupportConstant {
    public static HashSupport<Long> LONG = AbstractHash::putLong;
    public static HashSupport<Integer> INT = AbstractHash::putInt;
    public static HashSupport<Short> SHORT = AbstractHash::putShort;
    public static HashSupport<Byte> BYTE = AbstractHash::putByte;
    public static HashSupport<Float> FLOAT = AbstractHash::putFloat;
    public static HashSupport<Double> DOUBLE = AbstractHash::putDouble;
    public static HashSupport<Character> CHAR = AbstractHash::putChar;
    public static HashSupport<byte[]> BYTES = AbstractHash::putBytes;
    public static HashSupport<ByteBuffer> BETE_BUFFER = AbstractHash::putBytes;
    public static HashSupport<CharSequence> CHAR_SEQUENCE = AbstractHash::putChars;

    public static HashSupport<CharSequence> stringSupport(Charset charset){
        return (AbstractHash hash, CharSequence instance) -> {
            hash.putString(instance, charset);
        };
    }
}
