package com.github.hitzaki.bloom;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.Funnels;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 主应用程序
 * @author hitzaki
 */
public class MainApplication {
    public static void main(String[] args) {
        BloomFilter<CharSequence> bloomFilter =
                BloomFilter.create(Funnels.stringFunnel(StandardCharsets.UTF_8),
                        1000, 0.1);
    }
}
