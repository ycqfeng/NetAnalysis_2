package han_simulatorComponents;

import printControlComponents.InterfacePrintControlRegisterInstance;
import printControlComponents.PrintControl;

/**
 * Created by ycqfeng on 2016/12/4.
 */
public class Simulator implements InterfacePrintControlRegisterInstance {
    //时间
    double curTime;
    double stopTime;
    //事件链表
    Event eventQueueHead;
    Event passQueueTail;
    //注册实体接口
    SimulatorInterface[] interfaces;

    PrintControl printControl;

    //结束标志
    boolean executeFinish;

    //执行事件
    private void execute(){
        if (eventQueueHead == null){
            String str = "There is none in queue.";
            this.printControl.printlnErrorInfo(this,str);
            return;
        }
        Event curEvent;
        double progress;
        double difProgress = 1;
        long startPoint = System.currentTimeMillis();
        long endPoint;
        while (!this.isFinish(this.eventQueueHead)){
            this.curTime = this.eventQueueHead.getTimeExecute();
            if (this.stopTime > 0){
                progress = 100*this.curTime/this.stopTime;
                if (progress - difProgress > 1){
                    endPoint = System.currentTimeMillis();
                    System.out.println(progress+"%, 耗时："+(endPoint - startPoint)+"ms");
                    difProgress = Math.floor(progress);
                    startPoint = System.currentTimeMillis();
                }
            }
            curEvent = this.eventQueueHead;
            this.eventQueueHead = this.eventQueueHead.getNext();
            curEvent.setNextToNULL();
            curEvent.getEventInterface().run();
            this.addToPassQueueTail(curEvent);
        }

        /*double percent;
        int ipercent = 0;
        this.nextEvent = this.getAndMoveEventQueueHead();
        while (!this.isFinish(this.nextEvent)){
            this.curTime = this.nextEvent.getTimeExecute();
            if (this.stopTime > 0){
                percent = 100*this.curTime/this.stopTime;
                if (percent-ipercent>1){
                    System.out.println(percent+"%");
                    ipercent++;
                }
            }
            this.nextEvent.getEventInterface().run();
            this.addToPassQueueTail(this.nextEvent);
            this.nextEvent = this.getAndMoveEventQueueHead();
            if (this.nextEvent == null){
                break;
            }
        }*/
    }
    //开始准备并执行事件
    public void start(){
        //开始事件
        class EventStart implements EventInterface{
            Simulator simulator;
            public EventStart(Simulator simulator){
                this.simulator = simulator;
            }
            public void run(){
                this.simulator.executeFinish = false;
                System.out.println("Simulator starts.");
                if (this.simulator.interfaces != null){
                    for (int i = 0 ; i < this.simulator.interfaces.length ; i++){
                        this.simulator.interfaces[i].simulatorStart();
                    }
                }

            }
        }
        EventStart eventStart = new EventStart(this);
        Event event = new Event();
        event.setTimeExecute(0);
        event.setEventInterface(eventStart);
        this.addEvent(event);
        //结束事件
        class EventEnd implements EventInterface{
            Simulator simulator;
            public EventEnd(Simulator simulator){
                this.simulator = simulator;
            }
            public void run(){
                this.simulator.executeFinish = true;
                if (this.simulator.interfaces != null){
                    for (int i = 0 ; i < this.simulator.interfaces.length ; i++){
                        this.simulator.interfaces[i].simulatorEnd();
                    }
                }
                System.out.println("Simulator finish successfully.");
            }
        }
        if (this.stopTime > 0){
            EventEnd eventEnd = new EventEnd(this);
            event = new Event();
            event.setTimeExecute(this.stopTime);
            event.setEventInterface(eventEnd);
            this.addEvent(event);
        }
        //执行
        this.execute();
    }
    //增加一个注册实体
    public boolean register(SimulatorInterface simulatorInterface){
        if (this.isInterfaceEistence(simulatorInterface)){
            return false;
        }
        if (this.interfaces == null){
            this.interfaces = new SimulatorInterface[1];
            this.interfaces[0] = simulatorInterface;
            return true;
        }
        else{
            SimulatorInterface[] temp = new SimulatorInterface[this.interfaces.length+1];
            System.arraycopy(this.interfaces, 0, temp, 0, this.interfaces.length);
            temp[temp.length-1] = simulatorInterface;
            this.interfaces = temp;
            return true;
        }
    }
    public boolean isInterfaceEistence(SimulatorInterface simulatorInterface){
        if (this.interfaces == null){
            return false;
        }
        for (int i = 0 ; i < this.interfaces.length ; i++){
            if (this.interfaces[i] == simulatorInterface){
                return true;
            }
        }
        return false;
    }
    //增加一个事件
    public void addEvent(Event event){
        event.setSimulator(this);
        if (event.getTimeExecute() < this.curTime){
            String error = this.getClass().getName();
            error += "Event's timeExecute is less than this.curTime.";
            this.printControl.printlnErrorInfo(this,error);
        }
        Event temp = this.eventQueueHead;
        //若头非空
        if (temp != null){
            //若头小于事件，寻找下一个，直到尾部
            while (temp.getTimeExecute() < event.getTimeExecute()){
                if (temp.getNext() != null){
                    temp = temp.getNext();
                }
                else{
                    break;
                }
            }
            //若当前小于事件，插入当前后
            if (temp.getTimeExecute() < event.getTimeExecute()){
                temp.addToNext(event);
            }
            else{
                temp.addToLast(event);
            }
            this.eventQueueHead = this.eventQueueHead.getHead();
            return;
        }
        //若头为空
        else{
            this.eventQueueHead = event;
            return;
        }
    }
    public void addEvent(double interTime, EventInterface eventInterface){
        Event event = new Event();
        event.setInterTime(interTime);
        event.setEventInterface(eventInterface);
        this.addEvent(event);
    }
    //是否完成
    public boolean isFinish(Event event){
        if (this.executeFinish){
            return true;
        }
        if (this.stopTime != 0){
            return this.stopTime < event.getTimeExecute();
        }
        else{
            return false;
        }
    }
    //事件列表操作
    private void addToPassQueueTail(Event event){
        if (this.passQueueTail == null){
            this.passQueueTail = event;
            return;
        }
        this.passQueueTail.addToEnd(event);
        this.passQueueTail = this.passQueueTail.getEnd();
    }
    //设置、获取停止时间
    public void setStopTime(double stopTime){
        this.stopTime = stopTime;
    }
    public double getStopTime(){
        return this.stopTime;
    }
    //获取当前时间
    public double getCurTime(){
        return this.curTime;
    }

    public void setPrintControl(PrintControl printControl) {
        this.printControl = printControl;
        this.printControl.register(this);
    }
}
