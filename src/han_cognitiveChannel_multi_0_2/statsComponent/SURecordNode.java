package han_cognitiveChannel_multi_0_2.statsComponent;

/**
 * Created by ycqfeng on 2016/12/7.
 */
public class SURecordNode extends StatsNode{
    private boolean[] state;
    private boolean[] initalState;
    private int sumSubChannelsNumber;
    private double timeStart;
    private double timeEnd;
    private double timeDuration;
    private boolean isComplete;
    private boolean isCognitive;

    public boolean[] getInitalState() {
        return initalState;
    }

    public boolean isCognitive() {
        return isCognitive;
    }

    public void setCognitive(boolean cognitive) {
        isCognitive = cognitive;
    }

    public void setState(int index, boolean state){
        if (index >= this.sumSubChannelsNumber){
            System.out.println(this.getClass().getName() + "error in recording.");
        }
        this.state[index] = state;

    }

    public final boolean[] getState(){
        return this.state;
    }

    public void setSumSubChannelsNumber(int sumSubChannelsNumber) {
        this.sumSubChannelsNumber = sumSubChannelsNumber;
        this.state = new boolean[sumSubChannelsNumber];
        this.initalState = new boolean[sumSubChannelsNumber];
        for (int i = 0 ; i < sumSubChannelsNumber ; i++){
            this.state[i] = false;
            this.initalState[i] = false;
        }
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

    public int getSumSubChannelsNumber() {
        return sumSubChannelsNumber;
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

    public SURecordNode getHead(){
        return (SURecordNode) super.getHead();
    }

    public SURecordNode getEnd(){
        return (SURecordNode) super.getEnd();
    }

    public SURecordNode getLast(){
        return (SURecordNode) super.getLast();
    }

    public SURecordNode getNext(){
        return (SURecordNode) super.getNext();
    }
}
