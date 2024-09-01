package com.github.hitzaki.bloom.filter;
/**
 * TODO
 * @author hitzaki
 */
public class BloomFilter {

    public static void main(String[] args) {
        System.out.println(optimalNumOfBits(100, 0.01));
        System.out.println();
    }



    static long optimalNumOfBits(long n, double p) {
        // 初始值
        if (n == 0) {
            n = 1;
        }
        if (p == 0.0) {
            p = Double.MIN_VALUE;
        }

        return (long)((double)(-n) * Math.log(p) / (Math.log(2.0) * Math.log(2.0)));
    }
}
