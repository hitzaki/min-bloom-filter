
import com.github.hitzaki.bloom.base.hash.HashSupportConstant;
import com.github.hitzaki.bloom.filter.BloomFilter;

import java.nio.charset.StandardCharsets;

/**
 * TODO
 * @author hitzaki
 */
public class TestFun {
    public static void main(String[] args) {

        int size=10000;
        double fpp=0.0001;

        BloomFilter<CharSequence> bloomFilter =
                BloomFilter.create(HashSupportConstant.stringSupport(StandardCharsets.UTF_8),
                        10000, 0.02);
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



/*        BloomFilter<CharSequence> bloomFilter =
                BloomFilter.create(Funnels.stringFunnel(
                                StandardCharsets.UTF_8),
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
        System.out.println("误判数量："+count);*/
    }
}
