package han_cognitiveChannel_multi_0_2;

import han_cognitiveChannel_multi_0_2.statsComponent.SubChannelRecordNode;
import han_simulatorComponents.SimulatorInterface;
import printControlComponents.InterfacePrintControlRegisterInstance;
import printControlComponents.PrintControl;

/**
 * Created by ycqfeng on 2016/12/4.
 */
public class SubChannel implements SimulatorInterface, InterfacePrintControlRegisterInstance {
    private Channel channel;
    private int indexChannel;

    private InterfaceSubChannelNotify[] notifies;

    boolean occupyState;

    //记录
    SubChannelRecordNode records;

    PrintControl printControl;

    public SubChannel(PrintControl printControl){
        this.printControl = printControl;
    }
    public void setChannel(Channel channel, int indexChannel){
        if (channel == null){
            String str = this.getClass().getName();
            str += "The Reference(channel) is equal to null.";
            this.printControl.printlnErrorInfo(this,str);
            return;
        }
        this.channel = channel;
        this.indexChannel = indexChannel;
        this.notifies = null;
        this.occupyState = false;
    }

    public SubChannelRecordNode getRecords() {
        if (this.records == null){
            return null;
        }
        else{
            return records.getHead();
        }
    }

    public void releaseSubChannel(){
        //完成之前记录
        if (this.records != null){
            if (!this.records.isComplete()){
                this.records.setTimeEnd(this.channel.getSimulator().getCurTime());
            }
        }
        this.occupyState = false;
        //创建新纪录
        if (this.records == null){
            this.records = new SubChannelRecordNode();
            this.records.setOccupyState(this.occupyState);
            this.records.setTimeStart(this.channel.getSimulator().getCurTime());
        }
        else{
            SubChannelRecordNode nextRecordNode = new SubChannelRecordNode();
            nextRecordNode.setOccupyState(this.occupyState);
            nextRecordNode.setTimeStart(this.channel.getSimulator().getCurTime());
            this.records.addSingleToEnd(nextRecordNode);
            this.records = this.records.getEnd();
        }

    }

    public void occupySubChannel(){
        //完成之前记录
        if (this.records != null){
            if (!this.records.isComplete()){
                this.records.setTimeEnd(this.channel.getSimulator().getCurTime());
            }
        }
        this.occupyState = true;
        //创建新纪录
        if (this.records == null){
            this.records = new SubChannelRecordNode();
            this.records.setOccupyState(this.occupyState);
            this.records.setTimeStart(this.channel.getSimulator().getCurTime());
        }
        else{
            SubChannelRecordNode nextRecordNode = new SubChannelRecordNode();
            nextRecordNode.setOccupyState(this.occupyState);
            nextRecordNode.setTimeStart(this.channel.getSimulator().getCurTime());
            this.records.addSingleToEnd(nextRecordNode);
            this.records = this.records.getEnd();
        }

    }

    public void addInterfaceSubChannelNotify(InterfaceSubChannelNotify interfaceSubChannelNotify){
        if (interfaceSubChannelNotify == null){
            String str = this.getClass().getName();
            str += "The Reference(interfaceSubChannelNotify) is equal to null.";
            this.printControl.printlnErrorInfo(this,str);
            return;
        }
        //无已经存在的notify
        if (this.notifies == null){
            this.notifies = new InterfaceSubChannelNotify[1];
            this.notifies[0] = interfaceSubChannelNotify;
            return;
        }
        //已经存在notify
        InterfaceSubChannelNotify[] temp = this.notifies;
        this.notifies = new InterfaceSubChannelNotify[temp.length+1];
        System.arraycopy(temp, 0,this.notifies, 0, temp.length);
        this.notifies[this.notifies.length-1] = interfaceSubChannelNotify;
        return;
    }

    public Channel getChannel() {
        return channel;
    }

    public int getIndexChannel() {
        return indexChannel;
    }

    public boolean isOccupyState(){
        return occupyState;
    }

    @Override
    public void simulatorStart() {
        String str;
        if (this.channel == null){
            str = this.getClass().getName();
            str += "Can't start the SubChannel "+ this.indexChannel+", Because this.channel is equal to null";
            this.printControl.printlnErrorInfo(this,str);
        }
        //记录
        this.records = new SubChannelRecordNode();
        this.records.setOccupyState(this.occupyState);
        this.records.setTimeStart(this.channel.getSimulator().getCurTime());
        //启动成功
        str = this.getClass().getName();
        str += "PrimaryUser start successfully.";
        this.printControl.printlnLogicInfo(this,str);
    }

    @Override
    public void simulatorEnd() {
        if (!this.records.isComplete()){
            this.records.setTimeEnd(this.channel.getSimulator().getCurTime());
        }

    }
}
