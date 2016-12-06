package han_cognitiveChannel_multi_0_2.statsComponent;

import han_simulatorComponents.Simulator;

/**
 * Created by ycqfeng on 2016/12/6.
 */
public abstract class StatsNode {
    StatsNode last;
    StatsNode next;

    public void addSingleToHead(StatsNode statsNode){
        if (!this.isSingleNode(statsNode)){
            System.out.print(this.getClass().getName());
            System.out.println(" This.statsNode is not single.");
        }
        StatsNode headNode = this;
        while (headNode.last != null){
            headNode = headNode.getLast();
        }
        headNode.addSingleToLast(statsNode);
    }

    public void addSingleToEnd(StatsNode statsNode){
        if (!this.isSingleNode(statsNode)){
            System.out.print(this.getClass().getName());
            System.out.println(" This.statsNode is not single.");
        }
        StatsNode endNode = this;
        while (endNode != null){
            endNode = endNode.getNext();
        }
        endNode.addSingleToNext(statsNode);
    }

    public void addSingleToNext(StatsNode statsNode){
        if (!this.isSingleNode(statsNode)){
            System.out.print(this.getClass().getName());
            System.out.println(" This.statsNode is not single.");
        }
        if (this.next == null){
            this.next = statsNode;
            this.next.last = this;
            return;
        }
        else{
            StatsNode tempNode = this.next;
            this.next = statsNode;
            this.next.last = this;
            tempNode.last = statsNode;
            tempNode.last.next = tempNode;
            return;
        }
    }

    public void addSingleToLast(StatsNode statsNode){
        if (!this.isSingleNode(statsNode)){
            System.out.print(this.getClass().getName());
            System.out.println(" This.statsNode is not single.");
        }
        if (this.last == null){
            this.last = statsNode;
            this.last.next = this;
            return;
        }
        else{
            StatsNode tempNode = this.last;
            this.last = statsNode;
            this.last.next = this;
            tempNode.next = statsNode;
            tempNode.next.last = tempNode;
            return;
        }

    }

    public static boolean isSingleNode(StatsNode statsNode){
        if (statsNode.next != null){
            return false;
        }
        if (statsNode.last != null){
            return false;
        }
        return true;

    }

    public StatsNode getLast() {
        return last;
    }

    public StatsNode getNext() {
        return next;
    }
}
