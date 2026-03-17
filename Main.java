import java.util.*;

class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println("Reservation ID: " + reservationId + " | Guest: " + guestName + " | Room: " + roomType);
    }
}

class BookingHistory {
    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    public void addReservation(Reservation r) {
        history.add(r);
    }

    public List<Reservation> getAllReservations() {
        return history;
    }
}

class BookingReportService {

    public void displayAllBookings(List<Reservation> reservations) {
        System.out.println("---- Booking History ----");
        for (Reservation r : reservations) {
            r.display();
        }
        System.out.println("--------------------------");
    }

    public void generateSummary(List<Reservation> reservations) {
        Map<String, Integer> summary = new HashMap<>();

        for (Reservation r : reservations) {
            summary.put(r.getRoomType(), summary.getOrDefault(r.getRoomType(), 0) + 1);
        }

        System.out.println("---- Booking Summary ----");
        for (Map.Entry<String, Integer> entry : summary.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
        System.out.println("--------------------------");
    }
}

public class Main {
    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("   Welcome to Book My Stay Application");
        System.out.println("   Hotel Booking System v8.0");
        System.out.println("=======================================\n");

        BookingHistory history = new BookingHistory();

        history.addReservation(new Reservation("R101", "Amit", "Single Room"));
        history.addReservation(new Reservation("R102", "Riya", "Suite Room"));
        history.addReservation(new Reservation("R103", "Karan", "Double Room"));
        history.addReservation(new Reservation("R104", "Neha", "Single Room"));

        BookingReportService reportService = new BookingReportService();

        reportService.displayAllBookings(history.getAllReservations());
        System.out.println();
        reportService.generateSummary(history.getAllReservations());
    }
}