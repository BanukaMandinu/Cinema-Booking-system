import java.io.*;
import java.util.*;

public class Theater {

    public static byte[] row_one = new byte[12]; //Row one (12)
    public static byte[] row_two = new byte[16]; //Row two (16)
    public static byte[] row_three = new byte[20]; //Row three (20)
    public static byte[] rows = {12, 16, 20}; // Available seats in each row
    public static Scanner input = new Scanner(System.in); // Creating an ArrayList called ticketList
    static ArrayList<Ticket> ticketList = new ArrayList<>(); //ArrayList (uses to add name,surname and email)

    public static void main(String[] args) {
        System.out.println("Welcome to the the Theatre");
        Arrays.fill(row_one, (byte) 0);
        Arrays.fill(row_two, (byte) 0);
        Arrays.fill(row_three, (byte) 0);
        byte price = 0;
        menu(); //calling the menu.
        getInput(price); //Calling the Option Menu
    }

    public static void menu() { // menu method will print the main menu and its options
        System.out.print("""
                ---------------------------------------------
                Please select an option:
                1) Buy a Ticket.
                2) Print seating area.
                3) Cancel ticket.
                4) List available seats.
                5) Save to file.
                6) Load from file.
                7) Print ticket information and total price.
                8) Sort tickets by price.
                    0) Quit.
                ---------------------------------------------
                """);
    }

    public static void getInput(int price) { // getInput method will get the inputs of user, that which process that should do.
        System.out.print("\nEnter option: ");
        error();
        byte option = input.nextByte();
        switch (option) {
            case 1 -> buy_tickets(); // calling buy tickets method
            case 2 -> print_seating_area(price); // calling printing area method
            case 3 -> cancel_ticket(price); //calling cancel ticket method
            case 4 -> show_available(price); // calling show_available method
            case 5 -> save(price); //calling save method
            case 6 -> load(price); // calling save method
            case 7 -> show_ticket_info(price); // calling show_ticket_info method
            case 8 -> sort(price); // this will sort to acceding oder according to the price
            case 0 -> quit(); // calling quite
            default -> {
                System.out.println("Wrong input, Try Again!\n"); // default option if the user entered  invalid input.
                getInput(price);
            }
        }
    }

    // the buy_ticket method is for get the row number and the seat number from the user.
    public static void buy_tickets() {
        System.out.print("\nInsert the ROW number[1-3]: ");
        error();
        byte row_number = input.nextByte();
        if (row_number > 4 || row_number < 0) {
            System.out.println("Invalid input. Try Again!!");
            buy_tickets();
        }
        System.out.print("Insert the SEAT number [1- " + rows[row_number - 1] + "]: ");
        error(); // validate input
        byte seat_number = input.nextByte();
        seatRange(row_number, seat_number);
        if (row_number == 1 && row_one[seat_number - 1] == 0) {
            row_one[seat_number - 1] = 1;
            process(row_number, seat_number);
        } else if (row_number == 2 && row_two[seat_number - 1] == 0) {
            row_two[seat_number - 1] = 1;
            process(row_number, seat_number);
        } else if (row_number == 3 && row_three[seat_number - 1] == 0) {
            row_three[seat_number - 1] = 1;
            process(row_number, seat_number);
        } else {
            System.out.println("The seat is not available. Try a different seat!! ");
            buy_tickets();
        }
    }

    // the process method will ask the user that need to buy another ticket and ask more information such as name,surname and email.
    public static void process(byte row_number, byte seat_number) {
        // Asking name,surname and email in task-12
        System.out.print("Enter the first name: ");
        // gets the name
        String name = input.next();
        System.out.print("Enter the surname: ");
        // gets the surname
        String surName = input.next();
        System.out.print("Enter the E-mail: ");
        // gets the email
        String email = input.next();
        System.out.print("Enter the ticket price: ");
        // validate the input
        error();
        // gets the price
        int price = input.nextInt();
        System.out.println("You have booked the seat. \n");
        // Creating objects of Person and Ticket
        Person person = new Person(name, surName, email);
        Ticket ticket = new Ticket(row_number, seat_number, price, person);
        // adding the user inputs to the ticketList
        ticketList.add(ticket);
        // Asking to continue again
        System.out.print("Do You want to book another seat? [Yes/No]: ");
        toContinueAgain(price);
    }

    //printing the seating area
    public static void print_seating_area(int price) {
        System.out.println("""
                \n\t*************
                \t*   STAGE   *
                \t*************""");
        byte i = 0, j = 0, r = 0;
        byte space = 0;
        while (space < 4) {
            space++;
            System.out.print(" ");
        }
        // for row one
        for (byte element : row_one) {
            i++;
            if (i == 7)
                System.out.print(" ");
            if (element == 1) System.out.print("X");
            else System.out.print("O");
        }
        // for row two
        System.out.println(" ");
        byte space1 = 0;
        while (space1 < 2) {
            space1++;
            System.out.print(" ");
        }
        //for row three
        for (byte element : row_two) {
            j++;
            if (j == 9)
                System.out.print(" ");
            if (element == 1) System.out.print("X");
            else System.out.print("O");
        }
        System.out.println(" ");
        for (byte element : row_three) {
            r++;
            if (r == 11)
                System.out.print(" ");
            if (element == 1) System.out.print("X");
            else System.out.print("O");
        }
        System.out.println(" ");
        getInput(price);
    }

    public static void cancel_ticket(int price) {
        System.out.print("\nInsert row Number [1-3]: ");
        error();
        byte row_number = input.nextByte();
        if (row_number > 4 || row_number < 0) {
            System.out.println("Invalid input. Try Again!!");
            cancel_ticket(price);
        }
        System.out.print("Insert the SEAT number [1- " + rows[row_number - 1] + "]: ");
        byte seat_number = input.nextByte();
        seatRange(row_number, seat_number);
        if (row_number == 1 && row_one[seat_number - 1] == 1) {
            row_one[seat_number - 1] = 0;
            removeList(row_number, seat_number);
            removeBookedSeats(price);
        } else if (row_number == 2 && row_two[seat_number - 1] == 1) {
            row_two[seat_number - 1] = 0;
            removeList(row_number, seat_number);
            removeBookedSeats(price);
        } else if (row_number == 3 && row_three[seat_number - 1] == 1) {
            row_three[seat_number - 1] = 0;
            removeList(row_number, seat_number);
            removeBookedSeats(price);
        } else {
            System.out.println("The seat in Not booked, already.");
            getInput(price);
        }
    }

    public static void removeList(byte row_number, byte seat_number) {
        for (int i = 0; i < ticketList.size(); i++) {
            if (ticketList.get(i).getRow() == row_number && ticketList.get(i).getSeat() == seat_number) {
                ticketList.remove(i);
                i--;
            }
        }
    }

    public static void removeBookedSeats(int price) {
        System.out.println("You have cancelled the booked seat. \n");
        System.out.print("Do You want to cancel another booked seat? [Yes/No]: "); // Asking to continue again
        toContinueAgain(price);
    }

    public static void show_available(int price) {
        System.out.print("Seats Available in row one: ");
        int i = 0;
        for (int element : row_one) {
            ++i;
            if (element == 1) {
                continue;
            }
            System.out.print(i);
            if (i < 12)
                System.out.print(", ");
            else System.out.println(".");
        }
        System.out.print("Seats Available in row two: ");
        int j = 0;
        for (int element : row_two) {
            ++j;
            if (element == 1) {
                continue;
            }
            System.out.print(j);
            if (j < 16)
                System.out.print(", ");
            else System.out.println(".");
        }
        System.out.print("Seats Available in row three: ");
        int s = 0;
        for (int element : row_three) {
            ++s;
            if (element == 1) {
                continue;
            }
            System.out.print(s);
            if (s < 20)
                System.out.print(", ");
            else System.out.println(".");
        }
        getInput(price);
    }

    public static void save(int price) {

        try {
            PrintWriter writer = new PrintWriter("bookedSeats.txt");
            for (byte element : row_one) {
                writer.println(element);
            }
            for (byte element : row_two) {
                writer.println(element);
            }
            for (byte element : row_three) {
                writer.println(element);
            }
            writer.close();
            System.out.println("The data has SAVED to the file.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        getInput(price);
    }

    public static void load(int price) {
        String fileName = "bookedSeats.txt";
        try {
            Scanner scanner = new Scanner(new File(fileName));
            for (int i = 0; i < row_one.length; i++)
                row_one[i] = scanner.nextByte();
            for (int i = 0; i < row_two.length; i++)
                row_two[i] = scanner.nextByte();
            for (int i = 0; i < row_three.length; i++)
                row_three[i] = scanner.nextByte();
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // Print the restored array
        System.out.println("Row one:   " + Arrays.toString(row_one));
        System.out.println("Row two:   " + Arrays.toString(row_two));
        System.out.println("Row three: " + Arrays.toString(row_three));
        System.out.println("\nYou have loaded the data.");
        getInput(price);

    }

    // This methods will show more information about the ticket
    public static void show_ticket_info(int price) {
        float total = 0;
        for (Ticket ticket : ticketList) {
            ticket.print();
            total += ticket.getPrice();
        }
        System.out.println("Total amount of tickets: " + total + "£");
        System.out.println();
        getInput(price);
    }

    // exiting the program.
    public static void sort(int price) {
        ArrayList<Ticket> newArray = ticketList;
        for (int i = 0; i < newArray.size(); i++) {
            for (int j = i + 1; j < newArray.size(); j++) {
                if (newArray.get(i).getPrice() > newArray.get(j).getPrice()) {
                    Ticket data = newArray.get(i);
                    newArray.set(i, newArray.get(j));
                    newArray.set(j, data);
                }
            }
        }
        float total = 0;
        for (Ticket ticket : newArray) {
            ticket.print();
            total += ticket.getPrice();
        }
        System.out.println("Total amount of tickets: " + total + "£");
        getInput(price);
    }

    // this method is used to validate the information.
    public static void error() {
        while (!input.hasNextInt()) {
            System.out.print("Invalid input:  ");
            input.next();
        }
    }

    // this method will check the seat range
    public static void seatRange(byte row_number, byte seat_number) {
        while (true) {
            if (row_number == 1) {
                if (seat_number > 0 && seat_number < 13) {
                    break;
                } else {
                    System.out.print("You have only 12 seats in row 1, Enter again :");
                    seat_number = input.nextByte();
                }
            } else if (row_number == 2) {
                if (seat_number > 0 && seat_number < 17) {
                    break;
                } else {
                    System.out.print("You have only 16 seats in row 2, Enter again :");
                    seat_number = input.nextByte();
                }
            } else if (row_number == 3) {
                if (seat_number > 0 && seat_number < 21) {
                    break;
                } else {
                    System.out.print("You have only 20 seats in row 3, Enter again :");
                    seat_number = input.nextByte();
                }
            }
        }
    }

    public static void toContinueAgain(int price) {
        while (true) {
            String another_ticket = input.next();
            if (Objects.equals(another_ticket.toLowerCase(), "yes")) {
                buy_tickets();
                break;
            }
            if (Objects.equals(another_ticket.toLowerCase(), "no")) {
                getInput(price);
                break;
            } else {
                System.out.print("Wrong option, choose Yes or No: ");
            }
        }
    }

    public static void quit() {
        System.out.println("You have exited the program.");
    }
}