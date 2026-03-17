import java.util.*;

class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;

    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getGuestName() {
        return guestName;
    }
}

class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public void increment(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
    }

    public void display() {
        System.out.println("Inventory:");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
    }
}

class BookingHistory {
    private Map<String, Reservation> history;

    public BookingHistory() {
        history = new HashMap<>();
    }

    public void add(Reservation r) {
        history.put(r.getReservationId(), r);
    }

    public Reservation get(String id) {
        return history.get(id);
    }

    public void remove(String id) {
        history.remove(id);
    }

    public void display() {
        System.out.println("Active Bookings:");
        for (Reservation r : history.values()) {
            System.out.println(r.getReservationId() + " | " + r.getGuestName() + " | " + r.getRoomId());
        }
    }
}

class CancellationService {
    private Stack<String> rollbackStack;
    private Set<String> cancelled;

    public CancellationService() {
        rollbackStack = new Stack<>();
        cancelled = new HashSet<>();
    }

    public void cancel(String reservationId, BookingHistory history, RoomInventory inventory) {

        if (cancelled.contains(reservationId)) {
            System.out.println("Cancellation Failed: Already cancelled -> " + reservationId);
            return;
        }

        Reservation r = history.get(reservationId);

        if (r == null) {
            System.out.println("Cancellation Failed: Invalid reservation -> " + reservationId);
            return;
        }

        rollbackStack.push(r.getRoomId());
        inventory.increment(r.getRoomType());
        history.remove(reservationId);
        cancelled.add(reservationId);

        System.out.println("Cancelled: " + reservationId + " | Room Released: " + r.getRoomId());
    }

    public void displayRollbackStack() {
        System.out.println("Rollback Stack:");
        for (String id : rollbackStack) {
            System.out.println(id);
        }
    }
}

public class Main {
    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("   Welcome to Book My Stay Application");
        System.out.println("   Hotel Booking System v10.0");
        System.out.println("=======================================\n");

        RoomInventory inventory = new RoomInventory();
        BookingHistory history = new BookingHistory();

        Reservation r1 = new Reservation("R101", "Amit", "Single Room", "SI1");
        Reservation r2 = new Reservation("R102", "Riya", "Double Room", "DO1");

        history.add(r1);
        history.add(r2);

        CancellationService service = new CancellationService();

        history.display();
        System.out.println();

        service.cancel("R101", history, inventory);
        service.cancel("R200", history, inventory);
        service.cancel("R101", history, inventory);

        System.out.println();
        history.display();
        System.out.println();
        inventory.display();
        System.out.println();
        service.displayRollbackStack();
    }
}