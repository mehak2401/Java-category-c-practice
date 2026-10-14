import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract class Ticket {
    protected int count;
    protected static final double CONVENIENCE_FEE = 20.0;

    public Ticket(int count) {
        this.count = count;
    }

    public abstract double getBasePrice();

    public double calculateTotalAmount() {
        return count * (getBasePrice() + CONVENIENCE_FEE);
    }
}

class RegularTicket extends Ticket {
    public RegularTicket(int count) {
        super(count);
    }

    @Override
    public double getBasePrice() {
        return 150.0;
    }
}

class PremiumTicket extends Ticket {
    public PremiumTicket(int count) {
        super(count);
    }

    @Override
    public double getBasePrice() {
        return 250.0;
    }
}

class ReclinerTicket extends Ticket {
    public ReclinerTicket(int count) {
        super(count);
    }

    @Override
    public double getBasePrice() {
        return 400.0;
    }
}

public class MovieTicketCounter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt()) return;

        int n = scanner.nextInt();
        List<TicketBooking> bookings = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String seatType = scanner.next();
            int count = scanner.nextInt();

            Ticket ticket = null;
            switch (seatType) {
                case "REGULAR":
                    ticket = new RegularTicket(count);
                    break;
                case "PREMIUM":
                    ticket = new PremiumTicket(count);
                    break;
                case "RECLINER":
                    ticket = new ReclinerTicket(count);
                    break;
            }
            if (ticket != null) {
                bookings.add(new TicketBooking(seatType, ticket));
            }
        }

        double grandTotal = 0.0;
        for (TicketBooking booking : bookings) {
            double amount = booking.ticket.calculateTotalAmount();
            grandTotal += amount;
            System.out.printf("%s: %.2f%n", booking.seatType, amount);
        }

        System.out.printf("Total: %.2f%n", grandTotal);
        scanner.close();
    }

    private static class TicketBooking {
        String seatType;
        Ticket ticket;

        TicketBooking(String seatType, Ticket ticket) {
            this.seatType = seatType;
            this.ticket = ticket;
        }
    }
}