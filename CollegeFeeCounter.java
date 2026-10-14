import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

interface TransportUser {
    double getTransportFee();
}

abstract class Student {
    protected String name;

    public Student(String name) {
        this.name = name;
    }

    public abstract double getTuitionFee();

    public double calculateTotalFee() {
        double total = getTuitionFee();
        if (this instanceof TransportUser) {
            total += ((TransportUser) this).getTransportFee();
        }
        return total;
    }

    public String getName() {
        return name;
    }
}

class DayScholarStudent extends Student implements TransportUser {
    public DayScholarStudent(String name) {
        super(name);
    }

    @Override
    public double getTuitionFee() {
        return 40000.0;
    }

    @Override
    public double getTransportFee() {
        return 12000.0;
    }
}

class HostellerStudent extends Student {
    public HostellerStudent(String name) {
        super(name);
    }

    @Override
    public double getTuitionFee() {
        return 40000.0 + 60000.0; // Tuition + Hostel fee
    }
}

class ScholarshipStudent extends Student implements TransportUser {
    public ScholarshipStudent(String name) {
        super(name);
    }

    @Override
    public double getTuitionFee() {
        return 20000.0; // Half tuition
    }

    @Override
    public double getTransportFee() {
        return 12000.0;
    }
}

public class CollegeFeeCounter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt()) return;

        int n = scanner.nextInt();
        List<Student> students = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String type = scanner.next();
            String name = scanner.next();

            switch (type) {
                case "DAY_SCHOLAR":
                    students.add(new DayScholarStudent(name));
                    break;
                case "HOSTELLER":
                    students.add(new HostellerStudent(name));
                    break;
                case "SCHOLAR":
                    students.add(new ScholarshipStudent(name));
                    break;
            }
        }

        double totalCollected = 0.0;
        for (Student s : students) {
            double fee = s.calculateTotalFee();
            totalCollected += fee;
            System.out.printf("%s: %.2f%n", s.getName(), fee);
        }

        System.out.printf("Total Collected: %.2f%n", totalCollected);
        scanner.close();
    }
}