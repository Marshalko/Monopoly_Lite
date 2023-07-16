package sk.stuba.fei.uim.oop.model;

import java.util.Scanner;

public class Dice {
    public static int rollADice() {
        int thrown = (int) (Math.random() * 6 + 1);
        System.out.println("Rolled: " + thrown);
        return thrown;
    }
}
