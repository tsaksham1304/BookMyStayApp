import java.util.*;

abstract class Room {
    protected String roomType;
    protected int beds;
    protected double price;

    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Price: ₹" + price);
    }
}

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 2000);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 3500);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 6000);
    }
}

class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println("Guest: " + guestName + " | Room: " + roomType);
    }
}

class BookingQueue {
    private Queue<Reservation> queue;

    public BookingQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
    }

    public void displayQueue() {
        System.out.println("---- Booking Requests (FIFO Order) ----");
        for (Reservation r : queue) {
            r.display();
        }
        System.out.println("--------------------------------------");
    }
}

public class Main {
    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("   Welcome to Book My Stay Application");
        System.out.println("   Hotel Booking System v5.0");
        System.out.println("=======================================\n");

        BookingQueue bookingQueue = new BookingQueue();

        bookingQueue.addRequest(new Reservation("Amit", "Single Room"));
        bookingQueue.addRequest(new Reservation("Riya", "Suite Room"));
        bookingQueue.addRequest(new Reservation("Karan", "Double Room"));
        bookingQueue.addRequest(new Reservation("Neha", "Single Room"));

        bookingQueue.displayQueue();

        System.out.println("\nRequests stored successfully. No allocation done yet.");
    }
}