package han_cognitiveChannel_multi_0_2;

/**
 * Created by ycqfeng on 2016/12/6.
 */
public class RandomNumber {
    //指数分布
    public static double getRandomExp(double lambda){
        double z;
        z = Math.random();
        z = -(1/lambda)*Math.log(z);
        return z;
    }
}
