public class Ship {
    private final int x;
    private final int y;
    private boolean isHit;
    public Ship(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void hit() {
        isHit = true;
    }
    public boolean isHit() {
        return isHit;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}
