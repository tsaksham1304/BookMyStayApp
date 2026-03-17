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
}

class AddOnService {
    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }
}

class AddOnServiceManager {
    private Map<String, List<AddOnService>> serviceMap;

    public AddOnServiceManager() {
        serviceMap = new HashMap<>();
    }

    public void addService(String reservationId, AddOnService service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);
    }

    public double calculateTotalCost(String reservationId) {
        double total = 0;
        List<AddOnService> services = serviceMap.getOrDefault(reservationId, new ArrayList<>());
        for (AddOnService s : services) {
            total += s.getCost();
        }
        return total;
    }

    public void displayServices(String reservationId) {
        List<AddOnService> services = serviceMap.getOrDefault(reservationId, new ArrayList<>());
        System.out.println("Services for Reservation " + reservationId + ":");
        for (AddOnService s : services) {
            System.out.println(s.getServiceName() + " - ₹" + s.getCost());
        }
        System.out.println("Total Add-On Cost: ₹" + calculateTotalCost(reservationId));
    }
}

public class Main {
    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("   Welcome to Book My Stay Application");
        System.out.println("   Hotel Booking System v7.0");
        System.out.println("=======================================\n");

        Reservation r1 = new Reservation("R101", "Amit", "Single Room");
        Reservation r2 = new Reservation("R102", "Riya", "Suite Room");

        AddOnServiceManager manager = new AddOnServiceManager();

        manager.addService(r1.getReservationId(), new AddOnService("Breakfast", 500));
        manager.addService(r1.getReservationId(), new AddOnService("Airport Pickup", 1200));

        manager.addService(r2.getReservationId(), new AddOnService("Spa", 2000));
        manager.addService(r2.getReservationId(), new AddOnService("Dinner", 800));

        manager.displayServices(r1.getReservationId());
        System.out.println();
        manager.displayServices(r2.getReservationId());
    }
}