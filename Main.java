import java.util.*;

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

class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public synchronized boolean allocate(String roomType) {
        int available = inventory.getOrDefault(roomType, 0);
        if (available > 0) {
            inventory.put(roomType, available - 1);
            return true;
        }
        return false;
    }

    public void display() {
        System.out.println("Final Inventory:");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
    }
}

class BookingQueue {
    private Queue<Reservation> queue;

    public BookingQueue() {
        queue = new LinkedList<>();
    }

    public synchronized void addRequest(Reservation r) {
        queue.offer(r);
    }

    public synchronized Reservation getNext() {
        return queue.poll();
    }
}

class BookingProcessor extends Thread {
    private BookingQueue queue;
    private RoomInventory inventory;

    public BookingProcessor(BookingQueue queue, RoomInventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    public void run() {
        while (true) {
            Reservation r;
            synchronized (queue) {
                r = queue.getNext();
            }

            if (r == null) break;

            boolean success = inventory.allocate(r.getRoomType());

            if (success) {
                System.out.println(Thread.currentThread().getName() + " confirmed " + r.getGuestName() + " -> " + r.getRoomType());
            } else {
                System.out.println(Thread.currentThread().getName() + " failed " + r.getGuestName() + " -> " + r.getRoomType());
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("   Welcome to Book My Stay Application");
        System.out.println("   Hotel Booking System v11.0");
        System.out.println("=======================================\n");

        BookingQueue queue = new BookingQueue();
        RoomInventory inventory = new RoomInventory();

        queue.addRequest(new Reservation("Amit", "Single Room"));
        queue.addRequest(new Reservation("Riya", "Single Room"));
        queue.addRequest(new Reservation("Karan", "Single Room"));
        queue.addRequest(new Reservation("Neha", "Suite Room"));
        queue.addRequest(new Reservation("Raj", "Double Room"));

        BookingProcessor t1 = new BookingProcessor(queue, inventory);
        BookingProcessor t2 = new BookingProcessor(queue, inventory);
        BookingProcessor t3 = new BookingProcessor(queue, inventory);

        t1.setName("Thread-1");
        t2.setName("Thread-2");
        t3.setName("Thread-3");

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
        }

        System.out.println();
        inventory.display();
    }
}