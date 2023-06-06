public class Field {
    private boolean isHit;
    private int shipID;
    public Field() {
        isHit = false;
        shipID = -1;
    }
    public int shipID() {
        return shipID;
    }
    public void setShip(int shipID) {
        this.shipID = shipID;
    }
    public void hit() {
        isHit = true;
    }
    public boolean isHit() {
        return isHit;
    }
}
