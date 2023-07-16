package sk.stuba.fei.uim.oop.gameobjects;

public class GameChance extends GameCell {
    private GameChanceEffect effect;

    public GameChance() {
        this.effect = GameChanceEffect.getRandomEffect();

        super.type = GameCellType.CHANCE;
    }

    public GameChanceEffect getEffect() {
        return effect;
    }
}
