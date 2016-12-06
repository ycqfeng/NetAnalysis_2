package han_cognitiveChannel_multi_0_2.statsComponent;

import han_cognitiveChannel_multi_0_2.SubChannel;

/**
 * Created by ycqfeng on 2016/12/6.
 */
public class SubChannelRecordNode extends StatsNode{
    private boolean occupyState;
    private double timeStart;
    private double timeEnd;
    private double timeDuration;
    private boolean isComplete;

    public SubChannelRecordNode getHead(){
        return (SubChannelRecordNode) super.getHead();
    }

    public SubChannelRecordNode getEnd(){
        return (SubChannelRecordNode) super.getEnd();
    }

    public SubChannelRecordNode getLast(){
        return (SubChannelRecordNode) super.getLast();
    }

    public SubChannelRecordNode getNext(){
        return (SubChannelRecordNode) super.getNext();
    }

    public void setOccupyState(boolean occupyState) {
        this.occupyState = occupyState;
    }

    public void setTimeStart(double timeStart) {
        this.timeStart = timeStart;
        this.isComplete = false;
    }

    public void setTimeEnd(double timeEnd) {
        this.timeEnd = timeEnd;
        this.timeDuration = this.timeEnd - this.timeStart;
        this.isComplete = true;
    }

    public boolean getOccupyState() {
        return occupyState;
    }

    public double getTimeStart() {
        return timeStart;
    }

    public double getTimeEnd() {
        return timeEnd;
    }

    public double getTimeDuration() {
        return timeDuration;
    }

    public boolean isComplete() {
        return isComplete;
    }
}
