import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

interface NightServiceable {
    double applyNightSurcharge(double baseFare);
}

abstract class Cab {
    protected String type;

    public Cab(String type) {
        this.type = type;
    }

    public abstract double getRatePerKm();

    public double calculateFare(double km, String time) {
        double baseFare = km * getRatePerKm();
        baseFare = Math.max(100.0, baseFare); // Minimum fare rule

        if (time.equalsIgnoreCase("NIGHT")) {
            if (this instanceof NightServiceable) {
                return ((NightServiceable) this).applyNightSurcharge(baseFare);
            } else {
                return -1.0; // Rejected trip signal
            }
        }

        return baseFare;
    }

    public String getType() {
        return type;
    }
}

class MiniCab extends Cab {
    public MiniCab() {
        super("MINI");
    }

    @Override
    public double getRatePerKm() {
        return 10.0;
    }
}

class SedanCab extends Cab implements NightServiceable {
    public SedanCab() {
        super("SEDAN");
    }

    @Override
    public double getRatePerKm() {
        return 14.0;
    }

    @Override
    public double applyNightSurcharge(double baseFare) {
        return baseFare * 1.20; // 20% surcharge
    }
}

class SuvCab extends Cab implements NightServiceable {
    public SuvCab() {
        super("SUV");
    }

    @Override
    public double getRatePerKm() {
        return 18.0;
    }

    @Override
    public double applyNightSurcharge(double baseFare) {
        return baseFare * 1.20; // 20% surcharge
    }
}

public class CityCabFareMeter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt()) return;

        int n = scanner.nextInt();
        List<TripRecord> trips = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String cabType = scanner.next();
            double km = scanner.nextDouble();
            String time = scanner.next();

            Cab cab = null;
            switch (cabType) {
                case "MINI":
                    cab = new MiniCab();
                    break;
                case "SEDAN":
                    cab = new SedanCab();
                    break;
                case "SUV":
                    cab = new SuvCab();
                    break;
            }
            if (cab != null) {
                trips.add(new TripRecord(cab, km, time));
            }
        }

        double grandTotal = 0.0;
        for (TripRecord trip : trips) {
            double fare = trip.cab.calculateFare(trip.km, trip.time);
            if (fare < 0) {
                System.out.printf("%s: night service not available%n", trip.cab.getType());
            } else {
                grandTotal += fare;
                System.out.printf("%s: %.2f%n", trip.cab.getType(), fare);
            }
        }

        System.out.printf("Total: %.2f%n", grandTotal);
        scanner.close();
    }

    private static class TripRecord {
        Cab cab;
        double km;
        String time;

        TripRecord(Cab cab, double km, String time) {
            this.cab = cab;
            this.km = km;
            this.time = time;
        }
    }
}