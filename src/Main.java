import java.util.Random;
public class Main {
    private static final Random random = new Random();
    private static final Field[][] field = new Field[10][10];
    private static final Ship[][] ships = new Ship[10][5];
    public static void main(String[] args) {
        //Methode ist größtenteils Testcode - nicht final
        for(int f = 0; f < 10; f++) {
            for(int k = 0; k < 10; k++) {
                field[f][k] = new Field();
            }
        }
        field[random.nextInt(9)][random.nextInt(9)].hit();
        randShip(4);
        randShip(3);
        randShip(3);
        randShip(2);
        randShip(2);
        randShip(2);
        deploy(0, 4, 9, 9);
        printField();
        System.out.println();
        showShip();
        for(int i = 0; i < ships[0].length; i++) {
            if (ships[0][i] == null) break;
            fire(ships[0][i].getX(), ships[0][i].getY());
        }
        System.out.println(isWin());
        for(int j = 0; j < ships.length; j++) {
            for(int i = 0; i < ships[j].length; i++) {
                if (ships[j][i] == null) break;
                fire(ships[j][i].getX(), ships[j][i].getY());
            }
        }
        System.out.println(isWin());
    }
    public static void fire(int x, int y) {
        if (field[x][y].isHit()) {
            System.out.println("Already hit.");
            return;
        }
        field[x][y].hit();
        if (field[x][y].shipID() != -1) {
            for(int i = 0; i < 4; i++) {
                if(!ships[field[x][y].shipID()][i].isHit()) {
                    ships[field[x][y].shipID()][i].hit();
                    break;
                }
            }
            for(int i = 0; i < ships[field[x][y].shipID()].length; i++) {
                if (ships[field[x][y].shipID()][i] == null) break;
                if (!ships[field[x][y].shipID()][i].isHit()) {
                    System.out.println("Hit!");
                    return;
                }
            }
            System.out.println("Sunk!");
        } else {
            System.out.println("Miss!");
        }
    }
    public static void printField() {
        for(int i = 0; i < 10; i++) {
            for(int n = 0; n < 10; n++) {
                System.out.print(field[i][n].isHit() ? "X" : "O");
            }
            System.out.println();
        }
    }
    public static boolean isWin() {
        for(int i = 0; i < 10; i++) {
            for(int n = 0; n < 10; n++) {
                if (field[i][n].shipID() != -1) {
                    for(int j = 0; j < 4; j++) {
                        if (ships[field[i][n].shipID()][j] == null) break;
                        if (!ships[field[i][n].shipID()][j].isHit()) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
    public static void deploy(int direction, int length, int originX, int originY) {
        if (direction < 0 || direction > 3) {
            System.out.println("Invalid direction.");
            return;
        }
        if (length < 1 || length > 4) {
            System.out.println("Invalid length.");
            return;
        }
        if (originX < 0 || originX > 9 || originY < 0 || originY > 9) {
            System.out.println("Invalid origin.");
            return;
        }
        int x = originX;
        int y = originY;
        int n = 0;
        while(n < ships.length) {
            if (ships[n][0] == null) {
                break;
            } else if (n == ships.length - 1) {
                System.out.println("All ships deployed.");
                return;
            }
            n++;
        }
        for(int i = 0; i < length; i++) {
            if (x > 9 || x < 0 || y > 9 || y < 0) {
                deleteShip(n);
                System.out.println("Out of bounds.");
                return;
            }
            if (field[x][y].shipID() != -1) {
                deleteShip(n);
                System.out.println("Ship already there.");
                return;
            }
            field[x][y].setShip(n);
            ships[n][i] = new Ship(x, y);
            switch (direction) {
                case 0 -> x++;
                case 1 -> y++;
                case 2 -> x--;
                case 3 -> y--;
            }
        }
    }

    public static void deleteShip(int shipID) {
        for(int j = 0; j < ships[shipID].length; j++) {
            if (ships[shipID][j] == null) break;
            field[ships[shipID][j].getX()][ships[shipID][j].getY()].setShip(-1);
            ships[shipID][j] = null;
        }
    }

    public static void randShip(int length) {
        int direction = random.nextInt(3);
        int originX = random.nextInt(9);
        int originY = random.nextInt(9);
        deploy(direction, length, originX, originY);
    }
    public static void showShip() {
        for(int i = 0; i < 10; i++) {
            for(int n = 0; n < 10; n++) {
                System.out.print(field[i][n].shipID() != -1 ? "X" : "O");
            }
            System.out.println();
        }
    }
}