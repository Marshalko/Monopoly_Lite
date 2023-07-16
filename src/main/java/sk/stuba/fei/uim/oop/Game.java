package sk.stuba.fei.uim.oop;

import  sk.stuba.fei.uim.oop.gameobjects.*;
import  sk.stuba.fei.uim.oop.gameobjects.*;
import  sk.stuba.fei.uim.oop.model.Dice;
import  sk.stuba.fei.uim.oop.model.Player;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private GameBoard gameBoard;
    private List<Player> players;

    private int currentTurn = 0;
    private Player currentPlayerTurn;
    private Player winner = null;

    public Game(List<Player> players) {
        this.gameBoard = new GameBoard(24);

        this.players = players;

        for (Player player: players) {
            gameBoard.moveToStart(player);
        }


    }

    public Player playAGame() {
        while (winner == null) {
            int activePlayers = 0;
            for (Player player: players) {


                this.currentPlayerTurn = player;
                if (!this.currentPlayerTurn.hasLostGame()) {
                    System.out.println("\n================= " + currentTurn + ". =================");
                    System.out.println(player.getName() + " turn, you have $" + player.getMoney());
                    playerTurn();
                }

                if (!this.currentPlayerTurn.hasLostGame()) {
                    activePlayers++;
                }
            }

            if (activePlayers <= 1) {

                for (Player player: players) {
                    if (!player.hasLostGame()) winner = player;
                }
            }

            currentTurn++;
        }
        return winner;

    }

    private void playerTurn() {
        if (currentPlayerTurn.isInJail()) {
            System.out.println("You are in jail " + currentPlayerTurn.getName());
            return;
        }

        boolean madeACycle = this.gameBoard.move(this.currentPlayerTurn, Dice.rollADice());
        boolean lostGame = false;

        GameCell currentPlayerCell = this.currentPlayerTurn.getPlayerPosition();
        printGameBoardToConsole();

        if (madeACycle) {
            System.out.println("You survived whole year, you will gain $100");
            currentPlayerTurn.earnMoney(100);
        } else if (currentPlayerCell.getType() == GameCellType.PRISON) {
            System.out.println("You went to jail, next time you should obey the law! You must take a break for 1");
            currentPlayerTurn.throwInThePit();
        } else if (currentPlayerCell.getType() == GameCellType.TAXES) {
            System.out.println("You must pay taxes $100");
            lostGame = currentPlayerTurn.spendMoney(100);
        } else if (currentPlayerCell.getType() == GameCellType.CHANCE) {
            GameChance chance = (GameChance) (currentPlayerCell);
            System.out.println("You have come across a chance");

            switch (chance.getEffect()) {
                case BACKWARD:
                    System.out.println("And you must move backward " + chance.getEffect().getValue() + " steps");
                    gameBoard.move(currentPlayerTurn, -chance.getEffect().getValue());
                    break;
                case FORWARD:
                    System.out.println("And you must move forward " + chance.getEffect().getValue() + " steps");
                    gameBoard.move(currentPlayerTurn, chance.getEffect().getValue());
                    break;
                case EARN:
                    System.out.println("And you earned $" + chance.getEffect().getValue());
                    currentPlayerTurn.earnMoney(chance.getEffect().getValue());
                    break;
                case SPEND:
                    System.out.println("And you lost $" + chance.getEffect().getValue());
                    currentPlayerTurn.spendMoney(chance.getEffect().getValue());
                    break;
            }
        } else if (currentPlayerCell.getType() == GameCellType.BUILDING) {
            GameBuilding village = (GameBuilding) (currentPlayerCell);

            if (village.getBuildingOwner() != null) {
                if (village.getBuildingOwner() != currentPlayerTurn) {
                    System.out.println("You are currently on" + village.getBuildingOwner().getName() + " village, called " + village.getName() +  "and you have to pay " + village.requestARent());
                    currentPlayerTurn.spendMoney(village.requestARent());
                    // Should a village owner get money?
                    // village.getBuildingOwner().earnMoney(village.requestARent());
                }
            } else {
                System.out.println(" You stumbled upon a village " + village.getName() + ", which costs $" + village.offerBuilding());

                if (currentPlayerTurn.getMoney() < village.offerBuilding()) {
                    System.out.println("But you are poor");
                } else {
                    System.out.println("Would you like to buy it?");

                    Scanner scanner = new Scanner(System.in);
                    String answer = scanner.nextLine();

                    if (answer.matches("(y)|(Y)|(YES)|(yes)|(ano)")) {
                        lostGame = currentPlayerTurn.buyBuilding(village);
                    }
                }
            }
        }

        if (lostGame) currentPlayerTurn.lostGame();
    }

    public void printGameBoardToConsole() {
        int index = 0;
        for (GameCell cell : this.gameBoard.getGameBoard()) {
            switch (cell.getType()) {
                case START:
                    printCell(index + ". START");
                    break;
                case JAILTIME:
                    printCell(index + ". POLICAJT");
                    break;
                case PRISON:
                    printCell(index + ". VAZANIE");
                    break;
                case TAXES:
                    printCell(index + ". ZAPLAT");
                    break;
                case BUILDING:
                    GameBuilding village = (GameBuilding) cell;
                    printCell(index + ". " + village.getName() + " " + village.offerBuilding());
                    break;
                case CHANCE:
                    printCell(index + ". ?");
                    break;
                case DEFAULT:
                    printCell(index + ". !!!");
                    break;
            }
            index++;
        }

        for (Player player: players) {
            System.out.println();
            int currentIndex = gameBoard.getGameBoard().indexOf(player.getPlayerPosition());
            for (int i = 0; i < gameBoard.getGameBoard().size(); i++) {

                if ( currentIndex == i) {
                    printCell("" + player.getName().toUpperCase().charAt(0));
                } else {
                    printCell("" + (i));
                }
            }
        }

        System.out.println();


    }

    private void printCell(String message) {
        System.out.print("| " + message + " |");
    }

}
