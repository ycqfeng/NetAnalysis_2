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
public class SecondaryUser implements InterfacePrintControlRegisterInstance,
        InterfaceSubChannelNotify, SimulatorInterface
{
    Simulator simulator;

    //信道
    Channel channel;
    boolean[] subChannelState;
    boolean inCognitivePhase;

    //时间
    double timeCognitive;
    double timeTrans;
    double timeFrame;

    //事件
    EventCognitiveArrive eventCognitiveArrive;
    EventCognitiveDepart eventCognitiveDepart;
    EventTransArrive eventTransArrive;
    EventTransDepart eventTransDepart;

    PrintControl printControl;

    public SecondaryUser(PrintControl printControl){
        this.printControl = printControl;
        this.printControl.register(this);

        this.simulator = null;
        this.channel = null;

        this.timeCognitive = 0;
        this.timeTrans = 0;
        this.timeFrame = 0;

        eventCognitiveArrive = new EventCognitiveArrive(this, this.printControl);
        eventCognitiveDepart = new EventCognitiveDepart(this, this.printControl);
        eventTransArrive = new EventTransArrive(this, this.printControl);
        eventTransDepart = new EventTransDepart(this, this.printControl);
    }

    public void setSimulator(Simulator simulator) {
        this.simulator = simulator;
        this.simulator.register(this);
    }

    public boolean isInCognitivePhase() {
        return inCognitivePhase;
    }

    public void setInCognitivePhase(boolean inCognitivePhase) {
        this.inCognitivePhase = inCognitivePhase;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
        this.subChannelState = new boolean[this.channel.getSumSubChannelsNumber()];
        for (int i = 0 ; i < this.subChannelState.length ; i++){
            this.channel.getSubChannel(i).addInterfaceSubChannelNotify(this);
            this.subChannelState[i] = this.channel.getSubChannel(i).isOccupyState();
        }
    }

    public void setTimeCognitive(double timeCognitive) {
        this.timeCognitive = timeCognitive;
        this.timeTrans = this.timeFrame - this.timeCognitive;
    }

    public void setTimeTrans(double timeTrans) {
        this.timeTrans = timeTrans;
        this.timeCognitive = this.timeFrame - this.timeTrans;
    }

    public void setTimeFrame(double timeFrame) {
        this.timeFrame = timeFrame;
    }

    public Simulator getSimulator() {
        return simulator;
    }

    public Channel getChannel() {
        return channel;
    }

    public double getTimeCognitive() {
        return timeCognitive;
    }

    public double getTimeTrans() {
        return timeTrans;
    }

    public double getTimeFrame() {
        return timeFrame;
    }

    public EventCognitiveArrive getEventCognitiveArrive() {
        return eventCognitiveArrive;
    }

    public EventCognitiveDepart getEventCognitiveDepart() {
        return eventCognitiveDepart;
    }

    public EventTransArrive getEventTransArrive() {
        return eventTransArrive;
    }

    public EventTransDepart getEventTransDepart() {
        return eventTransDepart;
    }

    public PrintControl getPrintControl() {
        return printControl;
    }

    @Override
    public void simulatorStart() {
        String str;
        if (this.simulator == null){
            str = this.getClass().getName();
            str += "Can't start the SecondaryUser, Because this.simulator is equal to null.";
            this.printControl.printlnErrorInfo(this, str);
        }
        if (this.channel == null){
            str = this.getClass().getName();
            str += "Can't start the SecondaryUser, Because this.channel is equal to null.";
            this.printControl.printlnErrorInfo(this, str);
        }
        if (this.timeCognitive + this.timeTrans != this.timeFrame){
            str = this.getClass().getName();
            str += "Can't start the SecondaryUser, Because this.time is wrong.";
            this.printControl.printlnErrorInfo(this, str);
        }
        //感知阶段开始
        Event nextCognitiveArrive = new Event();
        nextCognitiveArrive.setInterTime(0);
        nextCognitiveArrive.setEventInterface(this.eventCognitiveArrive);
        this.simulator.addEvent(nextCognitiveArrive);
        //启动成功
        str = this.getClass().getName();
        str += " started successfully.";
        this.printControl.printlnLogicInfo(this, str);
    }

    @Override
    public void simulatorEnd() {

    }

    @Override
    public void subChannelIsOccupy(SubChannel subChannel) {
        String str = this.simulator.getCurTime() +", ";
        str += "SubChannel(" + subChannel.getIndexChannel() +") ";
        str += "is been occupied.";
        this.printControl.printlnLogicInfo(this, str);
        this.subChannelState[subChannel.getIndexChannel()] = true;
    }

    @Override
    public void subChannelIsRelease(SubChannel subChannel) {
        String str = this.simulator.getCurTime() +", ";
        str += "SubChannel(" + subChannel.getIndexChannel() +") ";
        str += "is been released.";
        this.printControl.printlnLogicInfo(this, str);
        this.subChannelState[subChannel.getIndexChannel()] = false;
    }
}

//感知阶段开始
class EventCognitiveArrive implements EventInterface,InterfacePrintControlRegisterInstance{
    SecondaryUser secondaryUser;
    PrintControl printControl;

    public EventCognitiveArrive(SecondaryUser secondaryUser, PrintControl printControl){
        this.secondaryUser = secondaryUser;
        this.printControl = printControl;
        this.printControl.register(this);
    }

    @Override
    public void run() {

        this.secondaryUser.setInCognitivePhase(true);

        String str = this.secondaryUser.getSimulator().getCurTime() + "s, ";
        str += "Cognitive Phase started.";
        this.printControl.printlnLogicInfo(this, str);
        //感知阶段结束
        Event cognitiveDepart = new Event();
        cognitiveDepart.setInterTime(this.secondaryUser.getTimeCognitive());
        cognitiveDepart.setEventInterface(this.secondaryUser.getEventCognitiveDepart());
        this.secondaryUser.getSimulator().addEvent(cognitiveDepart);
        //传输阶段开始
        Event transArrive = new Event();
        transArrive.setInterTime(this.secondaryUser.getTimeCognitive());
        transArrive.setEventInterface(this.secondaryUser.getEventTransArrive());
        this.secondaryUser.getSimulator().addEvent(transArrive);
    }
}
//感知阶段结束
class EventCognitiveDepart implements EventInterface,InterfacePrintControlRegisterInstance{
    SecondaryUser secondaryUser;
    PrintControl printControl;

    public EventCognitiveDepart(SecondaryUser secondaryUser, PrintControl printControl){
        this.secondaryUser = secondaryUser;
        this.printControl = printControl;
        this.printControl.register(this);
    }

    @Override
    public void run() {
        String str = this.secondaryUser.getSimulator().getCurTime() + "s, ";
        str += "Cognitive Phase ended.";
        this.printControl.printlnLogicInfo(this, str);
    }
}
//传输阶段开始
class EventTransArrive implements EventInterface,InterfacePrintControlRegisterInstance{
    SecondaryUser secondaryUser;
    PrintControl printControl;

    public EventTransArrive(SecondaryUser secondaryUser, PrintControl printControl){
        this.secondaryUser = secondaryUser;
        this.printControl = printControl;
        this.printControl.register(this);
    }

    @Override
    public void run() {

        this.secondaryUser.setInCognitivePhase(false);

        String str = this.secondaryUser.getSimulator().getCurTime() + "s, ";
        str += "Transmit Phase started.";
        this.printControl.printlnLogicInfo(this, str);

        //传输阶段结束
        Event transDepart = new Event();
        transDepart.setInterTime(this.secondaryUser.getTimeTrans());
        transDepart.setEventInterface(this.secondaryUser.getEventTransDepart());
        this.secondaryUser.getSimulator().addEvent(transDepart);
        //感知阶段开始
        Event cognitiveArrive = new Event();
        cognitiveArrive.setInterTime(this.secondaryUser.getTimeTrans());
        cognitiveArrive.setEventInterface(this.secondaryUser.getEventCognitiveArrive());
        this.secondaryUser.getSimulator().addEvent(cognitiveArrive);
    }
}
//传输阶段结束
class EventTransDepart implements EventInterface,InterfacePrintControlRegisterInstance{
    SecondaryUser secondaryUser;
    PrintControl printControl;

    public EventTransDepart(SecondaryUser secondaryUser, PrintControl printControl){
        this.secondaryUser = secondaryUser;
        this.printControl = printControl;
        this.printControl.register(this);
    }

    @Override
    public void run() {
        String str = this.secondaryUser.getSimulator().getCurTime() + "s, ";
        str += "Transmit Phase ended.";
        this.printControl.printlnLogicInfo(this, str);
    }
}