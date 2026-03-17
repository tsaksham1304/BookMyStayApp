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
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrement(String roomType) {
        inventory.put(roomType, getAvailability(roomType) - 1);
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
}

class BookingQueue {
    private Queue<Reservation> queue;

    public BookingQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNext() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

class BookingService {
    private Set<String> allocatedRoomIds;
    private HashMap<String, Set<String>> roomAllocations;
    private int counter;

    public BookingService() {
        allocatedRoomIds = new HashSet<>();
        roomAllocations = new HashMap<>();
        counter = 1;
    }

    private String generateRoomId(String roomType) {
        String id = roomType.replace(" ", "").substring(0, 2).toUpperCase() + counter++;
        return id;
    }

    public void processBookings(BookingQueue queue, RoomInventory inventory) {
        while (!queue.isEmpty()) {
            Reservation r = queue.getNext();
            String type = r.getRoomType();

            if (inventory.getAvailability(type) > 0) {
                String roomId = generateRoomId(type);

                if (!allocatedRoomIds.contains(roomId)) {
                    allocatedRoomIds.add(roomId);

                    roomAllocations.putIfAbsent(type, new HashSet<>());
                    roomAllocations.get(type).add(roomId);

                    inventory.decrement(type);

                    System.out.println("Booking Confirmed: " + r.getGuestName() + " -> " + roomId);
                }
            } else {
                System.out.println("Booking Failed (No Availability): " + r.getGuestName() + " -> " + type);
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("   Welcome to Book My Stay Application");
        System.out.println("   Hotel Booking System v6.0");
        System.out.println("=======================================\n");

        RoomInventory inventory = new RoomInventory();
        BookingQueue queue = new BookingQueue();

        queue.addRequest(new Reservation("Amit", "Single Room"));
        queue.addRequest(new Reservation("Riya", "Suite Room"));
        queue.addRequest(new Reservation("Karan", "Double Room"));
        queue.addRequest(new Reservation("Neha", "Single Room"));
        queue.addRequest(new Reservation("Raj", "Single Room"));

        BookingService service = new BookingService();
        service.processBookings(queue, inventory);
    }
}