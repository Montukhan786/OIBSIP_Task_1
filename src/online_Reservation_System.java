import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class online_Reservation_System {

    private String username;
    private String user_id;
    int flag = 0;

    online_Reservation_System(String u_name,String u_id){
        username = u_name;
        user_id = u_id;
    }

//    public final void clrscr() {
//        try {
//            try {
//                final String os = System.getProperty("os.name");
//
//                if (os.contains("Windows")) {
//                    Runtime.getRuntime().exec("cls");
//                } else {
//                    Runtime.getRuntime().exec("clear");
//                }
//            } catch (final Exception e) {
//                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
//            }
//        } catch (final Exception es) {
//            // System.out.println("nothing here!");
//        }
//
//    }

    void checkId() {
        //clrscr();
        System.out.println("Welcome " + username);
        System.out.println();
        System.out.print("Please enter the Customer ID or PIN: ");
        Scanner id = new Scanner(System.in);
        String chk = id.nextLine();
        if (chk.equals(user_id)) {
            //clrscr();
            start();
        } else {
            System.out.println("=================================");
            System.out.println("Wrong Login!!");
            System.out.println("=================================");

            if (flag < 3) {
                flag++;
                checkId();
            }
        }
    }

    private ReservationSystem reservationSystem = new ReservationSystem();

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Make a reservation");
            System.out.println("2. View all reservations");
            System.out.println("3. Cancel a reservation");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: //  Make a Reservation
                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Date: ");
                    String date = scanner.nextLine();
                    System.out.print("Number of guests: ");
                    int numberOfGuests = scanner.nextInt();
                    scanner.nextLine();

                    Reservation reservation = reservationSystem.makeReservation(name, date, numberOfGuests);
                    System.out.println("Reservation made with ID " + reservation.getId());
                    break;
                case 2: //  View all reservations
                    System.out.println("Reservations:");

                    for (Reservation r : reservationSystem.getReservations()) {
                        System.out.println(r.getId() + "  -  " + r.getName() + "  -  " + r.getDate() + "  -  " + r.getNumberOfGuests());
                    }
                    break;
                case 3: //  Cancel a reservation
                    System.out.print("Reservation ID to cancel: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();

                    if (reservationSystem.cancelReservation(id)) {
                        System.out.println("Reservation cancelled");
                    } else {
                        System.out.println("Reservation not found");
                    }
                    break;
                case 4: //Exit
                    return;
                default: //Invalid
                    System.out.println("Invalid choice");
            }

            System.out.println();
        }
    }
}

class Reservation {
    private int id;
    private String name;
    private String date;
    private int numberOfGuests;

    public Reservation(int id, String name, String date, int numberOfGuests) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.numberOfGuests = numberOfGuests;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }
}


class ReservationSystem {
    private List<Reservation> reservations = new ArrayList<>();//data of user
    private int nextId = 1;

    public Reservation makeReservation(String name, String date, int numberOfGuests) {
        Reservation reservation = new Reservation(nextId++, name, date, numberOfGuests);
        reservations.add(reservation);
        return reservation;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public Reservation getReservationById(int id) {
        for (Reservation reservation : reservations) {
            if (reservation.getId() == id) {
                return reservation;
            }
        }
        return null;
    }

    public boolean cancelReservation(int id) {
        Reservation reservation = getReservationById(id);
        if (reservation != null) {
            reservations.remove(reservation); //remove from the arrayList
            return true;
        }
        return false; //else id is invalid
    }
}
