package sk.stuba.fei.uim.oop.gameobjects;

import sk.stuba.fei.uim.oop.model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameBoard {
    private List<GameCell> gameBoard = new ArrayList<>();
    private int numberOfCells;

    public GameBoard(int numberOfCells) {
        this.numberOfCells = numberOfCells;

        this.init();
    }

    public void init() {

        String[] dzediny = {"Milpos","Rozkovany","Cervenica","Krivany","Torysa","Brezovica","Kamenica","Pecovska","Lucka","Lutina","Orlov","Sabinov","Bratislava","Lipany","Londyn","Moskva"};

        for (int i = 0, x = 0; i < numberOfCells; i++) {
            if (i % (numberOfCells/4) == 0) {
                switch (i/(numberOfCells/4)) {
                    case 0:
                        this.gameBoard.add(new Start());
                        break;
                    case 1:
                        this.gameBoard.add(new GameJailTime());
                        break;
                    case 2:
                        this.gameBoard.add(new GamePrison());
                        break;
                    case 3:
                        this.gameBoard.add(new GameTaxes());
                        break;
                    default:
                        break;
                }
            } else if (i % (numberOfCells/4) == ((numberOfCells)/4) /2 ) {
                this.gameBoard.add(new GameChance());
            } else {
                this.gameBoard.add(new GameBuilding(100 + i * 100, 10 + i * 10, dzediny[x++]));
            }
        }
    }

    public List<GameCell> getGameBoard() {
        return gameBoard;
    }

    public void moveToStart(Player player) {
        player.setPlayerPosition(getGameBoard().get(0));
    }

    public boolean move(Player player, int edgeNumber) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press ENTER to make a move?");
        String go = scanner.nextLine();

        int currentIndex = getGameBoard().indexOf(player.getPlayerPosition());
        int moveToIndex = (currentIndex + edgeNumber) % (numberOfCells);

        if (moveToIndex < 0) moveToIndex = 0;

        player.setPlayerPosition(getGameBoard().get(moveToIndex));

        this.checkJail(player, moveToIndex);
        return edgeNumber > moveToIndex;
    }

    public void checkJail(Player player, int currentPosition) {
        if (currentPosition/(numberOfCells/4) == 1) {
            player.setPlayerPosition(getGameBoard().get((numberOfCells/4) * 2));
        }
    }
}
