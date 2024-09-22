package com.github.hitzaki.bloom.filter;

import com.github.hitzaki.bloom.base.BaseBitMap;
import com.github.hitzaki.bloom.base.LongUtils;
import com.github.hitzaki.bloom.base.hash.HashSupport;
import com.github.hitzaki.bloom.base.hash.Murmur3_128Hash;

/**
 * 哈希策略
 * @author hitzaki
 */
public enum HashStrategyEnum implements HashStrategy {
    Murmur3_128_STRATEGY(){
        @Override
        public <T> boolean put(T object, HashSupport<? super T> support, int numHashFunctions, BaseBitMap baseBitMap) {
            Murmur3_128Hash murmur3128Hash = new Murmur3_128Hash(0);
            support.put(murmur3128Hash, object);
            byte[] bytes = murmur3128Hash.hash().asBytes();
            long hash1 = lowerEight(bytes);
            long hash2 = upperEight(bytes);
            boolean bitsChanged = false;
            long combinedHash = hash1;

            for(int i = 0; i < numHashFunctions; ++i) {
                bitsChanged |= baseBitMap.set((combinedHash & Long.MAX_VALUE) % baseBitMap.getBitSize());
                combinedHash += hash2;
            }

            return bitsChanged;
        }

        @Override
        public <T> boolean mightContain(T object, HashSupport<? super T> support, int numHashFunctions, BaseBitMap baseBitMap) {
            Murmur3_128Hash murmur3128Hash = new Murmur3_128Hash(0);
            support.put(murmur3128Hash, object);
            byte[] bytes = murmur3128Hash.hash().asBytes();
            long hash1 = lowerEight(bytes);
            long hash2 = upperEight(bytes);

            long combinedHash = hash1;
            for (int i = 0; i < numHashFunctions; i++) {
                // Make the combined hash positive and indexable
                if (!baseBitMap.get((combinedHash & Long.MAX_VALUE) % baseBitMap.getBitSize())) {
                    return false;
                }
                combinedHash += hash2;
            }
            return true;
        }

        private /* static */ long lowerEight(byte[] bytes) {
            return LongUtils.fromBytes(
                    bytes[7], bytes[6], bytes[5], bytes[4], bytes[3], bytes[2], bytes[1], bytes[0]);
        }

        private /* static */ long upperEight(byte[] bytes) {
            return LongUtils.fromBytes(
                    bytes[15], bytes[14], bytes[13], bytes[12], bytes[11], bytes[10], bytes[9], bytes[8]);
        }
    };


}
