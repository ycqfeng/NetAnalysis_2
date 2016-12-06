package han_cognitiveChannel_multi_0_2.statsComponent;

/**
 * Created by ycqfeng on 2016/12/6.
 */
public class PURecordNode extends StatsNode {
    private int state;
    private double timeStart;
    private double timeEnd;
    private double timeDuration;
    private boolean isComplete;

    public PURecordNode getHead(){
        return (PURecordNode) super.getHead();
    }

    public PURecordNode getEnd(){
        return (PURecordNode) super.getEnd();
    }

    public PURecordNode getLast(){
        return (PURecordNode) super.getLast();
    }

    public PURecordNode getNext(){
        return (PURecordNode) super.getNext();
    }

    public boolean isComplete() {
        return isComplete;
    }

    public double getTimeDuration() {
        return timeDuration;
    }

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
        this.isComplete = false;
    }

    public double getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(double timeEnd) {
        this.timeEnd = timeEnd;
        this.timeDuration = this.timeEnd - this.timeStart;
        this.isComplete = true;
    }
}
