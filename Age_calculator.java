import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class DateOfBirthOrAgeCalculator {

    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Invalid number of arguments! You need to provide 4 arguments.");
            return;
        }

        String firstParam = args[0];  // "DOB=27-02-2001" or "AGE=19dlc10dlc0019"
        String refDateParam = args[1]; // "27-10-2024"
        String dateFormat = args[2];  // "DD-MM-YYYY"
        String delimiter = args[3];   // "-"

        // Format the date format provided by user to Java's DateTimeFormatter
        String formattedDateFormat = dateFormat.replace("DD", "dd")
                                               .replace("MM", "MM")
                                               .replace("YYYY", "yyyy")
                                               .replace("dlc", delimiter);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formattedDateFormat);

        try {
            LocalDate referenceDate = LocalDate.parse(refDateParam, formatter);

            if (firstParam.startsWith("DOB=")) {
                // Calculate Age from DOB
                String dobString = firstParam.substring(4); // Extract DOB
                LocalDate dob = LocalDate.parse(dobString, formatter);
                calculateAge(dob, referenceDate);

            } else if (firstParam.startsWith("AGE=")) {
                // Calculate DOB from Age
                String ageString = firstParam.substring(4); // Extract Age (assuming format "YY-MM-DD")
                String[] ageParts = ageString.split(delimiter);

                int years = Integer.parseInt(ageParts[2]);
                int months = Integer.parseInt(ageParts[1]);
                int days = Integer.parseInt(ageParts[0]);

                calculateDOB(years, months, days, referenceDate);

            } else {
                System.out.println("Invalid input. First argument should start with 'DOB=' or 'AGE='.");
            }
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format or values provided.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Function to calculate and print the age from a given DOB and reference date
    private static void calculateAge(LocalDate dob, LocalDate referenceDate) {
        if (dob.isAfter(referenceDate)) {
            System.out.println("Error: DOB cannot be after the reference date.");
            return;
        }

        Period age = Period.between(dob, referenceDate);
        System.out.printf("Age is %d years, %d months, and %d days.%n", age.getYears(), age.getMonths(), age.getDays());
    }

    // Function to calculate and print the DOB from a given age and reference date
    private static void calculateDOB(int years, int months, int days, LocalDate referenceDate) {
        // Subtract the age from the reference date to get the DOB
        LocalDate dob = referenceDate.minusYears(years).minusMonths(months).minusDays(days);
        System.out.println("Date of Birth is: " + dob);
    }
}