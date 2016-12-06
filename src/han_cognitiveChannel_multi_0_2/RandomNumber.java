package han_cognitiveChannel_multi_0_2;

import java.util.Random;

/**
 * Created by ycqfeng on 2016/12/6.
 */
public class RandomNumber {
    static Random random = new java.util.Random(1);
    //指数分布
    public static double getRandomExp(double lambda){
        double z;
        //z = Math.random();
        z = random.nextDouble();
        z = -(1/lambda)*Math.log(z);
        return z;
    }
}
