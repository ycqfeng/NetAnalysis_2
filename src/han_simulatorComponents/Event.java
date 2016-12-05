package han_simulatorComponents;

/**
 * Created by ycqfeng on 2016/12/4.
 */
public class Event {
    //时间
    private double timeExecute;
    //节点
    private Event last;
    private Event next;
    //调度器
    private Simulator simulator;
    private EventInterface eventInterface;

    //设置\获取接口
    public void setEventInterface(EventInterface eventInterface){
        this.eventInterface = eventInterface;
    }
    public  EventInterface getEventInterface(){
        return this.eventInterface;
    }
    //链表设置、获取
    public void setLastToNULL(){
        if (this.last != null){
            if (this.last.next != null){
                this.last.next = null;
            }
            this.last = null;
        }
    }
    public void setNextToNULL(){
        if (this.next != null){
            if (this.next.last != null){
                this.next.last = null;
            }
            this.next = null;
        }
    }
    public void addToEnd(Event event){
        Event temp = this.getEnd();
        temp.addToNext(event);
    }
    public void addToHead(Event event){
        Event temp = this.getHead();
        temp.addToLast(event);
    }
    public Event getEnd(){
        Event event = this;
        while(event.next != null){
            event = event.next;
        }
        return event;
    }
    public Event getHead(){
        Event event = this;
        while(event.last != null){
            event = event.last;
        }
        return event;
    }
    public void addToLast(Event event){
        if (this.last == null){
            this.last = event;
            this.last.next = this;
            return;
        }
        else{
            Event temp = this.getLast();
            this.last = event;
            this.last.next = event;
            temp.next = event;
            temp.next.last = temp;
            return;
        }
    }
    public void addToNext(Event event){
        if (this.next == null){
            this.next = event;
            this.next.last = this;
            return;
        }
        else{
            Event temp = this.getNext();
            this.next = event;
            this.next.last = this;
            temp.last = event;
            temp.last.next = temp;
            return;
        }
    }
    public Event getLast(){
        return this.last;
    }
    public Event getNext(){
        return this.next;
    }
    //设置、获取仿真器
    public Simulator getSimulator(){
        return this.simulator;
    }
    public void setSimulator(Simulator simulator){
        this.simulator = simulator;
    }
    //设置、获取执行时间
    public void setTimeExecute(double timeExecute){
        this.timeExecute = timeExecute;
    }
    public double getTimeExecute(){
        return this.timeExecute;
    }
    //设置间隔时间
    public void  setInterTime(double interTime){
        this.timeExecute = this.simulator.getCurTime() + interTime;
    }
}
