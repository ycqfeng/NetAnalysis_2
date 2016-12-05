package com.company;

import han_simulatorComponents.Simulator;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Simulator simulator = new Simulator();
        simulator.setStopTime(10);
        simulator.start();
    }
}
