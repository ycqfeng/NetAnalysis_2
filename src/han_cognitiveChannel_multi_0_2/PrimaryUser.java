package han_cognitiveChannel_multi_0_2;

import han_simulatorComponents.Event;
import han_simulatorComponents.EventInterface;
import han_simulatorComponents.Simulator;
import han_simulatorComponents.SimulatorInterface;
import printControlComponents.InterfacePrintControlRegisterInstance;
import printControlComponents.PrintControl;

/**
 * Created by ycqfeng on 2016/12/6.
 */
public class PrimaryUser implements SimulatorInterface, InterfaceSubChannelNotify, InterfacePrintControlRegisterInstance {
    Simulator simulator;
    //信道
    int indexSubChannel;
    SubChannel subChannel;
    //时间
    double rateArrive;
    double rateDepart;
    int jobQueueLength;

    //到达、离开事件
    EventArrive eventArrive;
    EventDepart eventDepart;

    PrintControl printControl;

    public PrimaryUser(PrintControl printControl){
        this.printControl = printControl;
        this.printControl.register(this);
        this.simulator = null;
        this.indexSubChannel = -1;
        this.subChannel = null;
        this.rateArrive = 0;
        this.rateDepart = 0;
        this.jobQueueLength = 0;
        this.eventArrive = new EventArrive(this, this.printControl);
        this.eventDepart = new EventDepart(this, this.printControl);
    }

    public EventArrive getEventArrive() {
        return eventArrive;
    }

    public EventDepart getEventDepart() {
        return eventDepart;
    }

    public Simulator getSimulator() {
        return simulator;
    }

    public void setSimulator(Simulator simulator) {
        this.simulator = simulator;
        this.simulator.register(this);
    }

    public double getRateArrive() {
        return rateArrive;
    }

    public double getRateDepart() {
        return rateDepart;
    }

    public void setParameter(double rateArrive, double rateDepart){
        this.rateArrive = rateArrive;
        this.rateDepart = rateDepart;
    }

    public int getJobQueueLength() {
        return jobQueueLength;
    }

    public void setJobQueueLength(int jobQueueLength) {
        this.jobQueueLength = jobQueueLength;
    }

    public void setSubChannel(Channel channel, int indexSubChannel){
        this.indexSubChannel = indexSubChannel;
        this.subChannel = channel.getSubChannel(indexSubChannel);
        return;
    }
    //队列赠一
    public void arriveToQueue(){
        this.jobQueueLength += 1;
        String str = this.getSimulator().getCurTime() +"s, One job arrive to PU.";
        this.printControl.printlnLogicInfo(this, str);
        //判断是否开始占用信道
        if (this.jobQueueLength == 1){
            this.subChannel.occupySubChannel();
        }
    }
    //队列减一
    public void departFromQueue(){
        this.jobQueueLength -= 1;
        String str = this.getSimulator().getCurTime() + "s, One job depart from PU.";
        this.printControl.printlnLogicInfo(this,str);
        //判断是否结束占用信道
        if (this.jobQueueLength == 0){
            this.subChannel.releaseSubChannel();
        }
    }
    @Override
    public void simulatorStart() {
        String str;
        if (this.simulator == null){
            str = this.getClass().getName();
             str += "Can't start the PrimaryUser, Because this.simulator is equal to null.";
            this.printControl.printlnErrorInfo(this,str);
        }
        if (this.subChannel == null){
            str = this.getClass().getName();
             str += "Can't start the PrimaryUser, Because this.subChannel is equal to null.";
            this.printControl.printlnErrorInfo(this,str);
        }
        if (this.rateArrive < 0){
            str = this.getClass().getName();
             str += "Can't start the PrimaryUser, Because this.rateArrive is less than zero.";
            this.printControl.printlnErrorInfo(this,str);
        }
        if (this.rateDepart < 0){
            str = this.getClass().getName();
             str += "Can't start the PrimaryUser, Because this.rateDepart is less than zero.";
            this.printControl.printlnErrorInfo(this,str);
        }
        if (this.jobQueueLength < 0){
            str = this.getClass().getName();
             str += "Can't start the PrimaryUser, Because this.jobQueueLength is less than zero.";
            this.printControl.printlnErrorInfo(this,str);
        }
        Event nextArrive = new Event();
        nextArrive.setInterTime(RandomNumber.getRandomExp(this.getRateArrive()));
        nextArrive.setEventInterface(this.getEventArrive());
        this.simulator.addEvent(nextArrive);

        if (this.jobQueueLength > 0){
            Event nextDepart = new Event();
            nextDepart.setInterTime(RandomNumber.getRandomExp(this.getRateDepart()));
            nextDepart.setEventInterface(this.getEventDepart());
            this.simulator.addEvent(nextDepart);
        }


        str = this.getClass().getName();
        str += "PrimaryUser start successfully.";
        this.printControl.printlnLogicInfo(this,str);

    }

    @Override
    public void simulatorEnd() {

    }

    @Override
    public void subChannelIsOccupy(SubChannel subChannel) {

    }

    @Override
    public void subChannelIsRelease(SubChannel subChannel) {

    }

    //到达事件
    class EventArrive implements EventInterface, InterfacePrintControlRegisterInstance{
        PrimaryUser primaryUser;
        PrintControl printControl;

        public EventArrive(PrimaryUser primaryUser, PrintControl printControl){
            this.primaryUser = primaryUser;
            this.printControl = printControl;
            this.printControl.register(this);
        }

        @Override
        public void run() {
            //队列增一
            this.primaryUser.arriveToQueue();
            //下一个到达事件
            Event nextArrive = new Event();
            nextArrive.setInterTime(RandomNumber.getRandomExp(this.primaryUser.getRateArrive()));
            nextArrive.setEventInterface(this);
            this.primaryUser.getSimulator().addEvent(nextArrive);
            //若初次到达，对应的离开事件
            if (this.primaryUser.getJobQueueLength() == 1){
                Event nextDepart = new Event();
                nextDepart.setInterTime(RandomNumber.getRandomExp(this.primaryUser.getRateDepart()));
                nextDepart.setEventInterface(this.primaryUser.getEventDepart());
                this.primaryUser.getSimulator().addEvent(nextDepart);
            }


        }
    }
    //离开事件
    class EventDepart implements EventInterface, InterfacePrintControlRegisterInstance{
        PrimaryUser primaryUser;
        PrintControl printControl;

        public EventDepart(PrimaryUser primaryUser, PrintControl printControl){
            this.primaryUser = primaryUser;
            this.printControl = printControl;
            this.printControl.register(this);
        }
        @Override
        public void run() {
            //队列减一
            this.primaryUser.departFromQueue();
            //若队列非空，继续下一次离开事件
            if (this.primaryUser.getJobQueueLength() != 0){
                Event nextDepart = new Event();
                nextDepart.setInterTime(RandomNumber.getRandomExp(this.primaryUser.getRateDepart()));
                nextDepart.setEventInterface(this);
                this.primaryUser.getSimulator().addEvent(nextDepart);
            }

        }
    }
}
