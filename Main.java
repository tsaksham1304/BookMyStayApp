import java.util.*;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, -1);
    }

    public void validateRoomType(String roomType) throws InvalidBookingException {
        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }
    }

    public void validateAvailability(String roomType) throws InvalidBookingException {
        if (getAvailability(roomType) <= 0) {
            throw new InvalidBookingException("No availability for: " + roomType);
        }
    }

    public void decrement(String roomType) throws InvalidBookingException {
        int current = getAvailability(roomType);
        if (current <= 0) {
            throw new InvalidBookingException("Cannot decrement. No rooms left for: " + roomType);
        }
        inventory.put(roomType, current - 1);
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

class BookingService {

    public void processBooking(Reservation r, RoomInventory inventory) {
        try {
            inventory.validateRoomType(r.getRoomType());
            inventory.validateAvailability(r.getRoomType());

            inventory.decrement(r.getRoomType());

            System.out.println("Booking Confirmed: " + r.getGuestName() + " -> " + r.getRoomType());

        } catch (InvalidBookingException e) {
            System.out.println("Booking Failed: " + e.getMessage());
        }
    }
}

public class Main {
    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("   Welcome to Book My Stay Application");
        System.out.println("   Hotel Booking System v9.0");
        System.out.println("=======================================\n");

        RoomInventory inventory = new RoomInventory();
        BookingService service = new BookingService();

        Reservation r1 = new Reservation("Amit", "Single Room");
        Reservation r2 = new Reservation("Riya", "Suite Room");
        Reservation r3 = new Reservation("Karan", "Luxury Room");

        service.processBooking(r1, inventory);
        service.processBooking(r2, inventory);
        service.processBooking(r3, inventory);
    }
}