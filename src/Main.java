import java.util.Random;
import java.util.Scanner;
public class Main {
    private static final Random random = new Random();
    private static final Field[][] field = new Field[10][10];
    private static final Ship[][] ships = new Ship[10][5];
    private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        //Methode ist größtenteils Testcode - nicht final
        for(int f = 0; f < 10; f++) {
            for(int k = 0; k < 10; k++) {
                field[f][k] = new Field();
            }
        }
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
        /*for(int i = 0; i < ships[0].length; i++) {
            if (ships[0][i] == null) break;
            fire(ships[0][i].getX(), ships[0][i].getY());
        }
        System.out.println(isWin());
        for(int j = 0; j < ships.length; j++) {
            for(int i = 0; i < ships[j].length; i++) {
                if (ships[j][i] == null) break;
                fire(ships[j][i].getX(), ships[j][i].getY());
            }
        }*/
        System.out.println(isWin());
        while(!isWin()) {
            System.out.println("Enter x coordinate:");
            int x, y;
            try {
                x = scanner.nextInt();
                if(x < 0 || x > 9) {
                    System.out.println("Out of bounds..");
                    continue;
                }
                System.out.println("Enter y coordinate:");
                y = scanner.nextInt();
                if(y < 0 || y > 9) {
                    System.out.println("Out of bounds..");
                    continue;
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Expected integer.");
                scanner.nextLine();
                continue;
            }
            fire(x, y);
            printField();
        }
        System.out.println("You win!");
        scanner.close();
    }
    public static char fire(int x, int y) {
        if (field[x][y].isHit()) {
            System.out.println("Already hit.");
            return 'a';
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
                    return 'h';
                }
            }
            System.out.println("Sunk!");
            return 's';
        } else {
            System.out.println("Miss!");
            return 'm';
        }
    }
    public static void printField() {
        for(int i = 0; i < 10; i++) {
            for(int n = 0; n < 10; n++) {
                if (field[i][n].isHit()) {
                    if (field[i][n].shipID() != -1) {
                        System.out.print("X");
                    } else {
                        System.out.print("O");
                    }
                } else {
                    System.out.print("-");
                }
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
    public static boolean deploy(int direction, int length, int originX, int originY) {
        if (direction < 0 || direction > 3) {
            System.out.println("Invalid direction.");
            return false;
        }
        if (length < 1 || length > 4) {
            System.out.println("Invalid length.");
            return false;
        }
        if (originX < 0 || originX > 9 || originY < 0 || originY > 9) {
            System.out.println("Invalid origin.");
            return false;
        }
        int x = originX;
        int y = originY;
        int n = 0;
        while(n < ships.length) {
            if (ships[n][0] == null) {
                break;
            } else if (n == ships.length - 1) {
                System.out.println("All ships deployed.");
                return false;
            }
            n++;
        }
        for(int i = 0; i < length; i++) {
            if (x > 9 || x < 0 || y > 9 || y < 0) {
                deleteShip(n);
                System.out.println("Out of bounds.");
                return false;
            }
            if (field[x][y].shipID() != -1) {
                deleteShip(n);
                System.out.println("Ship already there.");
                return false;
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
        return true;
    }

    public static void deleteShip(int shipID) {
        for(int j = 0; j < ships[shipID].length; j++) {
            if (ships[shipID][j] == null) break;
            field[ships[shipID][j].getX()][ships[shipID][j].getY()].setShip(-1);
            ships[shipID][j] = null;
        }
    }

    public static void randShip(int length) {
        int direction, originX, originY;
        do {
            direction = random.nextInt(3);
            originX = random.nextInt(9);
            originY = random.nextInt(9);
        } while(!deploy(direction, length, originX, originY));
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