import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.Funnels;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 * @author hitzaki
 */
public class TestFun {
    public static void main(String[] args) {
        int bitIndex = 100;
        System.out.println(bitIndex / 64);
        System.out.println(bitIndex >>> 6);



        int size=10000;
        double fpp=0.0001;

        //没有设置误判率的情况下，10000→312，误判率3.12%
        BloomFilter<CharSequence> bloomFilter =
                BloomFilter.create(Funnels.stringFunnel(
                        Charset.forName("utf-8")),
                        10000, 0.01);
        for (int m=0;m<size;m++){
            bloomFilter.put(""+m);
        }

        int count = 0;

        for(int n=size+10000;n<size+20000;n++){
            if(bloomFilter.mightContain(""+n)){
                count++;
            }
        }
        System.out.println("误判数量："+count);
    }
}
