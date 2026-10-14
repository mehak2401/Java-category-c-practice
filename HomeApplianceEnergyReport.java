import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

interface SaverModeCapable {
    double applySaverDiscount(double baseUnits);
}

abstract class Appliance {
    protected String name;

    public Appliance(String name) {
        this.name = name;
    }

    public abstract double getPowerRatingWatts();

    public String getName() {
        return name;
    }
}

class FridgeAppliance extends Appliance {
    public FridgeAppliance() {
        super("FRIDGE");
    }

    @Override
    public double getPowerRatingWatts() {
        return 150.0;
    }
}

class AcAppliance extends Appliance implements SaverModeCapable {
    public AcAppliance() {
        super("AC");
    }

    @Override
    public double getPowerRatingWatts() {
        return 1500.0;
    }

    @Override
    public double applySaverDiscount(double baseUnits) {
        return baseUnits * 0.75; // 25% reduction
    }
}

class TvAppliance extends Appliance {
    public TvAppliance() {
        super("TV");
    }

    @Override
    public double getPowerRatingWatts() {
        return 100.0;
    }
}

class WasherAppliance extends Appliance implements SaverModeCapable {
    public WasherAppliance() {
        super("WASHER");
    }

    @Override
    public double getPowerRatingWatts() {
        return 500.0;
    }

    @Override
    public double applySaverDiscount(double baseUnits) {
        return baseUnits * 0.75; // 25% reduction
    }
}

public class HomeApplianceEnergyReport {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextLine()) return;

        String firstLine = scanner.nextLine().trim();
        if (firstLine.isEmpty()) return;
        int n = Integer.parseInt(firstLine);

        List<ApplianceRecord> records = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String line = scanner.nextLine().trim();
            String[] parts = line.split("\\s+");

            String type = parts[0];
            double hours = Double.parseDouble(parts[1]);
            boolean isSaver = (parts.length > 2 && parts[2].equalsIgnoreCase("SAVER"));

            Appliance appliance = null;
            switch (type) {
                case "FRIDGE":
                    appliance = new FridgeAppliance();
                    break;
                case "AC":
                    appliance = new AcAppliance();
                    break;
                case "TV":
                    appliance = new TvAppliance();
                    break;
                case "WASHER":
                    appliance = new WasherAppliance();
                    break;
            }
            if (appliance != null) {
                records.add(new ApplianceRecord(appliance, hours, isSaver));
            }
        }

        double totalCost = 0.0;
        for (ApplianceRecord record : records) {
            if (record.isSaver && !(record.appliance instanceof SaverModeCapable)) {
                System.out.printf("%s: saver mode not supported%n", record.appliance.getName());
            } else {
                double baseUnits = (record.appliance.getPowerRatingWatts() * record.hours) / 1000.0;
                double finalUnits = baseUnits;

                if (record.isSaver && record.appliance instanceof SaverModeCapable) {
                    finalUnits = ((SaverModeCapable) record.appliance).applySaverDiscount(baseUnits);
                }

                double cost = finalUnits * 8.0;
                totalCost += cost;
                System.out.printf("%s: Units=%.2f Cost=%.2f%n", record.appliance.getName(), finalUnits, cost);
            }
        }

        System.out.printf("Total Cost: %.2f%n", totalCost);
        scanner.close();
    }

    private static class ApplianceRecord {
        Appliance appliance;
        double hours;
        boolean isSaver;

        ApplianceRecord(Appliance appliance, double hours, boolean isSaver) {
            this.appliance = appliance;
            this.hours = hours;
            this.isSaver = isSaver;
        }
    }
}