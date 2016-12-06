package com.company;

import han_cognitiveChannel_multi_0_2.Channel;
import han_cognitiveChannel_multi_0_2.PrimaryUser;
import han_simulatorComponents.Simulator;
import printControlComponents.PrintControl;

public class Main {

    public static void main(String[] args) {
	// write your code here
        PrintControl printControl = new PrintControl();

        Simulator simulator = new Simulator();
        simulator.setPrintControl(printControl);
        simulator.setStopTime(10);

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

        System.out.println(simulator.getClass().getName());
    }
}
