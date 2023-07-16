package sk.stuba.fei.uim.oop.gameobjects;

import  sk.stuba.fei.uim.oop.model.Player;

public class GameBuilding extends GameCell {
    private Player owner = null;
    private int price;
    private int rent;
    private String name;

    public GameBuilding(int price, int rent, String name) {
        this.price = price;
        this.rent = rent;
        this.name = name;

        super.type = GameCellType.BUILDING;
    }

    public void buyABuilding(Player newOwner) {
        this.owner = newOwner;
    }

    public Player getBuildingOwner() {
        return this.owner;
    }

    public int requestARent() {
        return this.rent;
    }

    public int offerBuilding() {
        return this.price;
    }

    public String getName() {
        return this.name;
    }
}
