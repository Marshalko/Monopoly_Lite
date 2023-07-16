package sk.stuba.fei.uim.oop.gameobjects;

public enum GameChanceEffect {
    FORWARD(0, 6),
    BACKWARD(0,6),
    EARN(0, 1000),
    SPEND(0, 1000);

    private int min, max;
    private int value = 0;

    GameChanceEffect(int min, int max) {
        this.min = min;
        this.max = max;

        this.value = (int) (Math.random() * max + min);
    }

    public int getValue() {
        return value;
    }

    public static GameChanceEffect getRandomEffect() {
        switch ((int) (Math.random() * 4)) {
            case 0:
                return GameChanceEffect.FORWARD;
            case 1:
                return GameChanceEffect.BACKWARD;
            case 2:
                return GameChanceEffect.EARN;
            case 3:
                return GameChanceEffect.SPEND;
            default:
                return null;
        }
    }
}
