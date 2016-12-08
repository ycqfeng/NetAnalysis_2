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

        int SUMSUBCHANNEL = 9;

        PrintControl printControl = new PrintControl();

        Simulator simulator = new Simulator();
        simulator.setPrintControl(printControl);
        simulator.setStopTime(100000);

        Channel channel = new Channel(printControl);
        channel.setSimulator(simulator);
        channel.setChannel(SUMSUBCHANNEL);

        PrimaryUser[] primaryUsers = new PrimaryUser[SUMSUBCHANNEL];
        for (int i = 0 ; i < SUMSUBCHANNEL ; i++){
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

        int sum_cognitive = 0;
        int sum_init_occupy = 0;
        int sum_init_idle = 0;
        int sum_o_occupy = 0;
        int sum_o_idle = 0;
        int sum_i_occupy = 0;
        int sum_i_idle = 0;

        SURecordNode suRecordNode = secondaryUser.getRecords().getHead();
        while (suRecordNode != null){
            if (suRecordNode.isCognitive()){
                sum_cognitive++;
                for (int i = 0 ; i < suRecordNode.getInitalState().length ; i++){
                    if (suRecordNode.getInitalState()[i]){
                        sum_init_occupy++;
                        if (suRecordNode.getState()[i]){
                            sum_o_occupy++;
                        }
                        else{
                            sum_o_idle++;
                        }
                    }
                    else{
                        sum_init_idle++;
                        if (suRecordNode.getState()[i]){
                            sum_i_occupy++;
                        }
                        else{
                            sum_i_idle++;
                        }
                    }
                }
            }
            suRecordNode = suRecordNode.getNext();
        }
        System.out.println("sum_cognitive = "+sum_cognitive);
        System.out.println("sum_init_occupy = "+sum_init_occupy);
        System.out.println("sum_o_idle = "+sum_o_idle);
        System.out.println("sum_o_occupy = "+sum_o_occupy);

        System.out.println("sum_init_idle = "+sum_init_idle);
        System.out.println("sum_i_idle = "+ sum_i_idle);
        System.out.println("sum_i_occupy = "+sum_i_occupy);

        System.out.println(sum_i_idle/(double)sum_init_idle);
        System.out.println(sum_i_occupy/(double)sum_init_idle);


        long end = System.currentTimeMillis() - begin; // 这段代码放在程序执行后
        System.out.println("耗时：" + end + "毫秒");
    }
}
