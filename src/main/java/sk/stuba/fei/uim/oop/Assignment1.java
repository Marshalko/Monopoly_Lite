package sk.stuba.fei.uim.oop;

import sk.stuba.fei.uim.oop.model.Player;

import java.util.Scanner;

public class Assignment1 {
    public static void main(String[] args) {
        System.out.println("=======================================");
        System.out.println("|               WELCOME               |");
        System.out.println("=======================================");

        Scanner scanner = new Scanner(System.in);

        System.out.println("========= Zadaj pocet hracov =========");
        int numberOfPlayers = scanner.nextInt();

        Game game = new Game(Player.createPlayers(numberOfPlayers));
        Player winner = game.playAGame();

        System.out.println("====== HRA SKONCILA ======");

    }
}
