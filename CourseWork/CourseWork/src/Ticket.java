public class Ticket {
    // Attributes
    private int row;
    private int seat;
    private double price;
    private Person person;

    public Ticket(int row, int seat, double price, Person person) {
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }

    public int getRow() {
        return row;
    }

    public int getSeat() {
        return seat;
    }

    public double getPrice() {
        return price;
    }

    public void print() {
        System.out.println(" ");
        System.out.println("First Name: " + person.getName());
        System.out.println("SurName   : " + person.getSurname());
        System.out.println("Email     : " + person.getEmail());
        System.out.println("Row       : " + row);
        System.out.println("Seat      : " + seat);
        System.out.println("Price     : " + price + "£");
    }

}
