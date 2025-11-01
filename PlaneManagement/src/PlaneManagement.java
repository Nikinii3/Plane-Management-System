import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class PlaneManagement {
    private static final int[] ROW_SIZES = {14, 12, 12, 14}; // A,B,C,D
    private static final char[] ROW_LABELS = {'A', 'B', 'C', 'D'};
    private static final int[][] seats = new int[4][]; // 0=available, 1=sold
    private static final Ticket[] tickets = new Ticket[100]; // fixed storage
    private static int ticketCount = 0;
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to the Plane Management application");

        for (int i = 0; i < 4; i++) {
            seats[i] = new int[ROW_SIZES[i]];
        }

        while (true) {
            showMenu();
            int option = readInt("Choose option: ");
            switch (option) {
                case 0 -> { System.out.println("Exiting..."); return; }
                case 1 -> buySeat();
                case 2 -> cancelSeat();
                case 3 -> findFirstAvailable();
                case 4 -> showSeatingPlan();
                case 5 -> printTicketsInfo();
                case 6 -> searchTicket();
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\nMenu:");
        System.out.println("0 - Exit");
        System.out.println("1 - Buy seat");
        System.out.println("2 - Cancel seat");
        System.out.println("3 - Find first available");
        System.out.println("4 - Show seating plan");
        System.out.println("5 - Print tickets info");
        System.out.println("6 - Search ticket");
    }

    private static void buySeat() {
        char row = readRow();
        int seat = readSeat(row);

        int rowIndex = rowToIndex(row);
        if (seats[rowIndex][seat - 1] == 1) {
            System.out.println("Seat already sold!");
            return;
        }

        sc.nextLine();
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        System.out.print("Enter surname: ");
        String surname = sc.nextLine();
        System.out.print("Enter email: ");
        String email = sc.nextLine();
        Person p = new Person(name, surname, email);

        int price = getSeatPrice(row, seat);
        Ticket t = new Ticket(row, seat, price, p);

        seats[rowIndex][seat - 1] = 1;
        tickets[ticketCount++] = t;

        System.out.println("Seat booked successfully!");
        t.printInfo();
        t.save();
    }

    private static void cancelSeat() {
        char row = readRow();
        int seat = readSeat(row);

        int rowIndex = rowToIndex(row);
        if (seats[rowIndex][seat - 1] == 0) {
            System.out.println("Seat is not sold.");
            return;
        }
        seats[rowIndex][seat - 1] = 0;

        for (int i = 0; i < ticketCount; i++) {
            if (tickets[i].getRow() == row && tickets[i].getSeat() == seat) {
                tickets[i] = tickets[ticketCount - 1];
                tickets[ticketCount - 1] = null;
                ticketCount--;
                System.out.println("Seat cancelled successfully.");
                return;
            }
        }
    }

    private static void findFirstAvailable() {
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                if (seats[i][j] == 0) {
                    System.out.println("First available: " + ROW_LABELS[i] + (j + 1));
                    return;
                }
            }
        }
        System.out.println("No seats available.");
    }

    private static void showSeatingPlan() {
        for (int i = 0; i < seats.length; i++) {
            System.out.print(ROW_LABELS[i] + " ");
            for (int j = 0; j < seats[i].length; j++) {
                System.out.print((seats[i][j] == 0 ? "O " : "X "));
            }
            System.out.println();
        }
    }

    private static void printTicketsInfo() {
        int total = 0;
        for (int i = 0; i < ticketCount; i++) {
            tickets[i].printInfo();
            total += tickets[i].getPrice();
        }
        System.out.println("Total amount: £" + total);
    }

    private static void searchTicket() {
        char row = readRow();
        int seat = readSeat(row);

        for (int i = 0; i < ticketCount; i++) {
            if (tickets[i].getRow() == row && tickets[i].getSeat() == seat) {
                tickets[i].printInfo();
                return;
            }
        }
        System.out.println("This seat is available.");
    }

    private static int rowToIndex(char row) {
        return switch (row) {
            case 'A' -> 0; case 'B' -> 1; case 'C' -> 2; case 'D' -> 3;
            default -> -1;
        };
    }

    private static char readRow() {
        while (true) {
            System.out.print("Enter row (A-D): ");
            char r = sc.next().toUpperCase().charAt(0);
            if (r >= 'A' && r <= 'D') return r;
            System.out.println("Invalid row.");
        }
    }

    private static int readSeat(char row) {
        int rowIndex = rowToIndex(row);
        while (true) {
            int seat = readInt("Enter seat number: ");
            if (seat >= 1 && seat <= ROW_SIZES[rowIndex]) return seat;
            System.out.println("Invalid seat number.");
        }
    }

    private static int readInt(String prompt) {
        System.out.print(prompt);
        while (!sc.hasNextInt()) {
            sc.next();
            System.out.print("Invalid input, try again: ");
        }
        return sc.nextInt();
    }

    private static int getSeatPrice(char row, int seat) {
        if (row == 'A') return 200;
        if (row == 'B') return 150;
        if (row == 'C') return 180;
        return 100; // row D
    }
}

