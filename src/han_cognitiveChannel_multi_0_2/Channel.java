package han_cognitiveChannel_multi_0_2;

import han_simulatorComponents.Simulator;
import han_simulatorComponents.SimulatorInterface;
import printControlComponents.InterfacePrintControlRegisterInstance;
import printControlComponents.PrintControl;

/**
 * Created by ycqfeng on 2016/12/4.
 */
public class Channel implements SimulatorInterface, InterfacePrintControlRegisterInstance{
    //调度器
    private Simulator simulator;
    //子信道
    private SubChannel[] subChannels;
    private int sumSubChannelsNumber;
    //打印控制
    private PrintControl printControl;
    public Channel(PrintControl printControl){
        this.simulator = null;
        this.subChannels = null;
        this.sumSubChannelsNumber = 0;
        this.printControl = printControl;
        this.printControl.register(this);
    }
    public void setChannel(int sumSubChannelsNumber){
        if (sumSubChannelsNumber < 0){
            String str = this.getClass().getName();
            str += "The Parameter(sumSubChannelsNumber) must be a positive number.";
            this.printControl.printlnErrorInfo(this,str);
            return;
        }
        this.sumSubChannelsNumber = sumSubChannelsNumber;
        this.subChannels = new SubChannel[sumSubChannelsNumber];
        for (int i = 0 ; i < sumSubChannelsNumber ; i++){
            this.subChannels[i] = new SubChannel(this.printControl);
            this.subChannels[i].setChannel(this,i);
            this.getSimulator().register(this.subChannels[i]);
        }
    }
    public SubChannel getSubChannel(int indexSubChannel){
        if (indexSubChannel < 0){
            String str = this.getClass().getName();
            str += "The Parameter(indexSubChannel) is less than zero.";
            this.printControl.printlnErrorInfo(this,str);
            return null;
        }
        if (indexSubChannel > this.subChannels.length){
            String str = this.getClass().getName();
            str = "The Parameter(indexSubChannel) is larger than the number of subChannels.";
            this.printControl.printlnErrorInfo(this,str);
            return null;
        }
        return this.subChannels[indexSubChannel];
    }
    public Simulator getSimulator(){
        return this.simulator;
    }
    public int getSumSubChannelsNumber(){
        return this.sumSubChannelsNumber;
    }
    public void setSimulator(Simulator simulator){
        this.simulator = simulator;
        this.simulator.register(this);
        if (this.subChannels != null){
            for (int i = 0 ; i < this.subChannels.length ; i++){
                this.simulator.register(this.subChannels[i]);
            }
        }
    }
    //仿真器开始\结束
    @Override
    public void simulatorStart() {
        String str;
        str = this.getClass().getName();
        str += "function(simulatorStart).";
        this.printControl.printlnLogicInfo(this,str);
        if (this.simulator == null){
            str = this.getClass().getName();
            str += "this.simulator is equal to null.";
            this.printControl.printlnErrorInfo(this,str);
            return;
        }
        if (this.subChannels == null){
            str = this.getClass().getName();
            str += "this.subChannels is equal to null.";
            this.printControl.printlnErrorInfo(this,str);
        }

    }

    @Override
    public void simulatorEnd() {

    }
}
