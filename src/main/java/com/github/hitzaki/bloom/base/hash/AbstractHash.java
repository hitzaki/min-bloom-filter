package com.github.hitzaki.bloom.base.hash;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * 抽象哈希
 * @author hitzaki
 */
public abstract class AbstractHash {
    protected ByteBuffer byteBuffer;

    AbstractHash(int size){
        byteBuffer = ByteBuffer.allocate(size);
    }

    void putByte(byte byteValue){
        byteBuffer.put(byteValue);
        if (byteBuffer.position() == byteBuffer.limit()){
            processRemaining();
        }
    }

    void putBytes(byte[] bytesValue){
        for (byte item : bytesValue){
            putByte(item);
        }
    }

    void putBytes(ByteBuffer bf){
        if (bf.position()!=0){
            bf.flip();
        }
        while (bf.hasRemaining()){
            putByte(bf.get());
        }
    }

    void putShort(short shortValue){
        putByte((byte) shortValue);
        putByte((byte) (shortValue >>> 8));
    }

    void putInt(int intValue) {
        putByte((byte) intValue);
        putByte((byte) (intValue >>> 8));
        putByte((byte) (intValue >>> 16));
        putByte((byte) (intValue >>> 24));
    }

    void putLong(long longValue){
        putByte((byte) longValue);
        putByte((byte) (longValue >>> 8));
        putByte((byte) (longValue >>> 16));
        putByte((byte) (longValue >>> 24));
        putByte((byte) (longValue >>> 32));
        putByte((byte) (longValue >>> 40));
        putByte((byte) (longValue >>> 48));
        putByte((byte) (longValue >>> 56));
    }

    void putFloat(float floatValue) {
        putInt(Float.floatToRawIntBits(floatValue));
    }

    void putDouble(double doubleValue) {
        putLong(Double.doubleToRawLongBits(doubleValue));
    }

    void putBoolean(boolean bool) {
        putByte((byte) (bool? 1: 0));
    }

    void putChar(char charValue) {
        putByte((byte) charValue);
        putByte((byte) (charValue >>> 8));
    }

    void putChars(CharSequence chars) {
        for (int i=0; i<chars.length(); i++){
            putChar(chars.charAt(i));
        }
    }

    void putString(CharSequence charSequence, Charset charset){
        putBytes(charSequence.toString().getBytes(charset));
    }

    HashCode hash(){
        if (byteBuffer.hasRemaining()){
            processRemaining();
        }
        return makeHash();
    }

    abstract void processRemaining();

    abstract HashCode makeHash();
}
