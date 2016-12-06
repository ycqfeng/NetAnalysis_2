package han_cognitiveChannel_multi_0_2.statsComponent;

/**
 * Created by ycqfeng on 2016/12/6.
 */
public class PURecordNode extends StatsNode {
    private int state;
    private double timeStart;
    private double timeEnd;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public double getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(double timeStart) {
        this.timeStart = timeStart;
    }

    public double getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(double timeEnd) {
        this.timeEnd = timeEnd;
    }
}
