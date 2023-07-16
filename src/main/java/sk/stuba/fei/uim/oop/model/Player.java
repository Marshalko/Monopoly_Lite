package sk.stuba.fei.uim.oop.model;

import  sk.stuba.fei.uim.oop.gameobjects.GameBoard;
import  sk.stuba.fei.uim.oop.gameobjects.GameBuilding;
import  sk.stuba.fei.uim.oop.gameobjects.GameCell;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player {
    private int id;
    private GameCell playerPosition;

    private int money = 10000;
    private String name;

    private int prisonTime = 0;
    private boolean lostGame = false;

    public Player(String name) {
        this.name = name;
    }

    public Player(int money, String name) {
        this.money = money;
        this.name = name;
    }

    public int getMoney() {
        return money;
    }

    public String getName() {
        return name;
    }

    public void earnMoney(int amount) {
        this.money += amount;
    }

    private void loseMoney(int amount) {
        this.money -= amount;

        if (this.money < 0) this.money = 0;
    }

    public boolean spendMoney(int amount) {
        loseMoney(amount);

        return this.money <= 0;
    }

    public boolean buyBuilding(GameBuilding building) {
        building.buyABuilding(this);

        return spendMoney(building.offerBuilding());
    }

    public GameCell getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(GameCell playerPosition) {
        if (prisonTime <= 0) {
            this.playerPosition = playerPosition;
        }
    }

    public boolean isInJail() {
        return prisonTime-- > 0 ;
    }

    // Not my current turn but the next one
    public void throwInThePit() {
        prisonTime = 1;
    }

    public boolean hasLostGame() {
        return lostGame;
    }

    public void lostGame() {
        this.lostGame = true;
    }

    public static List<Player> createPlayers(int numberOfPlayers) {
        List<Player> players = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < numberOfPlayers; i++) {
            System.out.println("======== Zadaj meno " + i + ". hraca ========");
            String name = scanner.nextLine();

            Player newPlayer = new Player(name);
            players.add(newPlayer);
        }

        return players;
    }
}
