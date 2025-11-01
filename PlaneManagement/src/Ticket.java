import java.io.FileWriter;
import java.io.IOException;

public class Ticket {
    private char row;
    private int seat;
    private int price;
    private Person person;

    public Ticket(char row, int seat, int price, Person person) {
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }

    public char getRow() { return row; }
    public int getSeat() { return seat; }
    public int getPrice() { return price; }
    public Person getPerson() { return person; }

    public void setRow(char row) { this.row = row; }
    public void setSeat(int seat) { this.seat = seat; }
    public void setPrice(int price) { this.price = price; }
    public void setPerson(Person person) { this.person = person; }

    public void printInfo() {
        System.out.println("Ticket: Row " + row + ", Seat " + seat + ", Price £" + price);
        person.printInfo();
    }

    public void save() {
        String filename = row + "" + seat + ".txt";
        try (FileWriter fw = new FileWriter(filename)) {
            fw.write("Ticket: Row " + row + ", Seat " + seat + ", Price £" + price + "\n");
            fw.write("Person: " + person.getName() + " " + person.getSurname() + ", Email: " + person.getEmail());
        } catch (IOException e) {
            System.out.println("Error saving ticket file: " + e.getMessage());
        }
    }
}
