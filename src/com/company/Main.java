package com.company;

import han_cognitiveChannel_multi_0_2.Channel;
import han_cognitiveChannel_multi_0_2.PrimaryUser;
import han_cognitiveChannel_multi_0_2.statsComponent.PURecordNode;
import han_simulatorComponents.Simulator;
import printControlComponents.PrintControl;

public class Main {

    public static void main(String[] args) {
	// write your code here
        PrintControl printControl = new PrintControl();

        Simulator simulator = new Simulator();
        simulator.setPrintControl(printControl);
        simulator.setStopTime(1000);

        Channel channel = new Channel(printControl);
        channel.setSimulator(simulator);
        channel.setChannel(1);

        PrimaryUser primaryUser = new PrimaryUser(printControl);
        primaryUser.setSimulator(simulator);
        primaryUser.setJobQueueLength(0);
        primaryUser.setParameter(3, 7);
        primaryUser.setSubChannel(channel, 0);

        printControl.setPrintLogicInfoState(primaryUser, true);

        simulator.start();

        PURecordNode puRecordNode = primaryUser.getRecords();
        String str;
        double[] state = new double[10];
        while (puRecordNode != null){
            for (int i = 0 ; i < 10 ; i++){
                if (puRecordNode.getState() == i){
                    state[i] += puRecordNode.getTimeDuration();
                }
            }
            puRecordNode = puRecordNode.getNext();
        }
        double sum = 0;
        for (int i = 0 ; i < state.length ; i++){
            str = "stat("+(i+1)+")="+state[i]+";";
            sum +=state[i];
            System.out.println(str);
        }

        System.out.println(sum);
    }
}
