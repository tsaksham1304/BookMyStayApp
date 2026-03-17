import java.io.*;
import java.util.*;

class Reservation implements Serializable {
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

    public String toString() {
        return reservationId + " | " + guestName + " | " + roomType;
    }
}

class RoomInventory implements Serializable {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public void allocate(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) - 1);
    }

    public void display() {
        System.out.println("Inventory:");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
    }
}

class BookingHistory implements Serializable {
    private List<Reservation> bookings;

    public BookingHistory() {
        bookings = new ArrayList<>();
    }

    public void add(Reservation r) {
        bookings.add(r);
    }

    public void display() {
        System.out.println("Booking History:");
        for (Reservation r : bookings) {
            System.out.println(r);
        }
    }
}

class PersistenceService {

    public void save(RoomInventory inventory, BookingHistory history) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("data.ser"));
            out.writeObject(inventory);
            out.writeObject(history);
            out.close();
            System.out.println("Data saved successfully.");
        } catch (Exception e) {
            System.out.println("Error saving data.");
        }
    }

    public Object[] load() {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("data.ser"));
            RoomInventory inventory = (RoomInventory) in.readObject();
            BookingHistory history = (BookingHistory) in.readObject();
            in.close();
            System.out.println("Data loaded successfully.");
            return new Object[]{inventory, history};
        } catch (Exception e) {
            System.out.println("No previous data found. Starting fresh.");
            return null;
        }
    }
}

public class Main {
    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("   Welcome to Book My Stay Application");
        System.out.println("   Hotel Booking System v12.0");
        System.out.println("=======================================\n");

        PersistenceService ps = new PersistenceService();

        RoomInventory inventory;
        BookingHistory history;

        Object[] data = ps.load();

        if (data != null) {
            inventory = (RoomInventory) data[0];
            history = (BookingHistory) data[1];
        } else {
            inventory = new RoomInventory();
            history = new BookingHistory();
        }

        Reservation r1 = new Reservation("R201", "Amit", "Single Room");
        Reservation r2 = new Reservation("R202", "Riya", "Double Room");

        history.add(r1);
        history.add(r2);

        inventory.allocate("Single Room");
        inventory.allocate("Double Room");

        System.out.println();
        history.display();
        System.out.println();
        inventory.display();

        ps.save(inventory, history);
    }
}