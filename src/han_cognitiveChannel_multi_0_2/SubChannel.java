package han_cognitiveChannel_multi_0_2;

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

    public void releaseSubChannel(){

    }

    public void occupySubChannel(){

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

    }

    @Override
    public void simulatorEnd() {

    }
}
