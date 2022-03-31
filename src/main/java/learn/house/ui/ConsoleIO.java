package learn.house.ui;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

@Component
public class ConsoleIO {
    private final Scanner scanner = new Scanner(System.in);
    private final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public void print(String msg) { System.out.print(msg); }
    public void println(String msg) { System.out.println(msg); }
    public void printf(String msg, Object... obj) { System.out.printf(msg, obj); }

    public String readString(String prompt) {
        print(prompt);
        return scanner.nextLine();
    }

    public String readRequiredString(String prompt) {
        boolean valid = false;
        String result = "";

        do {
            result = readString(prompt);
            if(!result.isBlank()) {
                valid = true;
            }else{
                println("Please enter string.");
            }
        }while(!valid);

        return result;
    }

    public boolean readBoolean(String prompt) {
        boolean valid = false;
        String input = "";
        boolean result = false;

        {
            input = readRequiredString(prompt).toLowerCase();
            if(input.equals("y") || input.equals("n")) {
                if (input.equals("y")) {
                    result = true;
                } else if (input.equals("n")) {
                    result = false;
                }
                valid = true;
            }else {
                println("Please enter y or n");
            }
        }while(!valid);

        return result;
    }

    public int readInt(String prompt) {
        boolean valid = false;
        String result = "";
        int numericalResult = 0;

        do {
            result = readRequiredString(prompt);

            try {
                numericalResult = Integer.parseInt(result);
                valid = true;
            } catch(NumberFormatException e) {
                println("Input is not the correct format.");
            }
        }while (!valid);

        return numericalResult;
    }

    public int readInt(String prompt, int min, int max) {
        boolean valid = false;
        int result = 0;

        do {
            result = readInt(prompt);
            if(result >= min && result <= max) {
                valid = true;
            } else {
                println("Integer is out of bounds.");
            }
        }while (!valid);

        return result;
    }

    public LocalDate readRequiredLocalDate(String prompt) {
        boolean valid = false;
        String input = "";
        LocalDate result = null;

        do {
            input = readRequiredString(prompt);
            try {
                result = LocalDate.parse(input, timeFormat);
                valid = true;
            } catch (DateTimeParseException ex) {
                println("Date format is not valid.");
            }
        }while(!valid);

        return result;
    }

    public LocalDate readLocalDate(String prompt) {
        boolean valid = false;
        String input = "";
        LocalDate result = null;

        input = readString(prompt);

        try {
            result = LocalDate.parse(input, timeFormat);
        } catch (DateTimeParseException ex) {
            result = null;
        }

        return result;
    }

    public BigDecimal readBigDecimal(String prompt) {
        boolean valid = false;
        String input = "";
        BigDecimal result = null;

        do {
            input = readRequiredString(prompt);
            try {
                result = new BigDecimal(input);
                valid = true;
            } catch (NumberFormatException ex) {
                println("The number is invalid.");
            }
        }while(!valid);

        return result;
    }

    public BigDecimal readBigDecimal(String prompt, BigDecimal min, BigDecimal max) {
        boolean valid = false;
        BigDecimal result = null;

        do {
            result = readBigDecimal(prompt);
            if (result.compareTo(min) >= 0 && result.compareTo(max) <= 0) {
                valid = true;
            } else {
                println("Decimal value is out of range.");
            }
        }while(!valid);

        return result;
    }
}
