package sk.stuba.fei.uim.oop.gameobjects;

abstract public class GameCell {
    protected GameCellType type = GameCellType.DEFAULT;

    public GameCellType getType() {
        return type;
    }
}
