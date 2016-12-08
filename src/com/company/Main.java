package com.company;

import han_cognitiveChannel_multi_0_2.Channel;
import han_cognitiveChannel_multi_0_2.PrimaryUser;
import han_cognitiveChannel_multi_0_2.SecondaryUser;
import han_cognitiveChannel_multi_0_2.statsComponent.PURecordNode;
import han_cognitiveChannel_multi_0_2.statsComponent.SURecordNode;
import han_cognitiveChannel_multi_0_2.statsComponent.SubChannelRecordNode;
import han_simulatorComponents.Simulator;
import printControlComponents.PrintControl;

public class Main {

    public static void main(String[] args) {
	// write your code here
        long begin = System.currentTimeMillis(); // 这段代码放在程序执行前

        PrintControl printControl = new PrintControl();

        Simulator simulator = new Simulator();
        simulator.setPrintControl(printControl);
        simulator.setStopTime(100000);

        Channel channel = new Channel(printControl);
        channel.setSimulator(simulator);
        channel.setChannel(2);

        PrimaryUser[] primaryUsers = new PrimaryUser[2];
        for (int i = 0 ; i < 2 ; i++){
            primaryUsers[i] = new PrimaryUser(printControl);
            primaryUsers[i].setSimulator(simulator);
            primaryUsers[i].setJobQueueLength(0);
            primaryUsers[i].setParameter(3, 7);
            primaryUsers[i].setSubChannel(channel, i);

        }

        SecondaryUser secondaryUser = new SecondaryUser(printControl);
        secondaryUser.setSimulator(simulator);
        secondaryUser.setChannel(channel);
        secondaryUser.setTimeFrame(1);
        secondaryUser.setTimeCognitive(0.2);

        printControl.setPrintLogicInfoState(primaryUsers[0], false);
        printControl.setPrintLogicInfoState(primaryUsers[1], false);
        printControl.setPrintLogicInfoState(secondaryUser, false);
        printControl.setPrintLogicInfoState(secondaryUser.getEventCognitiveArrive(), false);
        printControl.setPrintLogicInfoState(secondaryUser.getEventCognitiveDepart(), false);
        printControl.setPrintLogicInfoState(secondaryUser.getEventTransArrive(), false);
        printControl.setPrintLogicInfoState(secondaryUser.getEventTransDepart(), false);

        simulator.start();

        SURecordNode suRecordNode = secondaryUser.getRecords().getHead();
        SURecordNode nextCognitive;
        int sum_o = 0;
        int sum_i = 0;
        int sum_a = 0;
        int sum_b = 0;
        while (suRecordNode != null){
            if (suRecordNode.isCognitive()){
                nextCognitive = suRecordNode.getNext();
                while (nextCognitive!=null){
                    if (nextCognitive.isCognitive()){
                        break;
                    }
                    nextCognitive = nextCognitive.getNext();
                }
                if (nextCognitive != null){
                    if (suRecordNode.getState()[0]){
                        if (nextCognitive.getState()[0])
                            sum_o++;
                        else
                            sum_i++;
                    }
                    else{
                        if (nextCognitive.getState()[0])
                            sum_a++;
                        else
                            sum_b++;
                    }
                }
            }
            suRecordNode = suRecordNode.getNext();
        }

        System.out.println(sum_o);
        System.out.println(sum_i/(double)sum_o);
        System.out.println(sum_i);
        System.out.println(sum_a);
        System.out.println(sum_b);
        System.out.println(sum_b/(double)sum_a);
        System.out.println(sum_i+sum_o+sum_b+sum_a);

        long end = System.currentTimeMillis() - begin; // 这段代码放在程序执行后
        System.out.println("耗时：" + end + "毫秒");
    }
}
