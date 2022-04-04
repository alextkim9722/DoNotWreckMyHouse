package learn.house.ui;

import learn.house.models.Guest;
import learn.house.models.Host;
import learn.house.models.Reservation;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class View {

    private ConsoleIO consoleIO;

    public View(ConsoleIO consoleIO) {
        this.consoleIO = consoleIO;
    }

    public MainMenuOptions mainMenuSelection() {
        displayHeader("Main Menu");

        int selection;
        int min = 0;
        int max = 1;

        for(MainMenuOptions option : MainMenuOptions.values()) {
            consoleIO.printf("%2d - %s\n", option.getValue(), option.getHeader());
            min = Math.min(min, option.getValue());
            max = Math.max(max, option.getValue());
        }

        selection = consoleIO.readInt(String.format("Enter a selection between %d - %d: ", min, max), min, max);
        return MainMenuOptions.fromValue(selection);
    }

    public String getIdentifier(String target) {
        return consoleIO.readRequiredString(String.format("Enter an identifier for %s. [email, p/n, ID]", target));
    }

    public void viewReservations(List<String> reservations) {
        if(reservations.isEmpty()) {
            consoleIO.println("No Reservations");
        }else {
            for (String reservation : reservations) {
                consoleIO.println(reservation);
            }
        }
    }

    public List<String> formatReservations(List<Reservation>  reservations) {
        List<Reservation> sortedReservations = reservations.stream()
                .sorted((a, b) -> a.getStart().compareTo(b.getStart()))
                .collect(Collectors.toList());
        List<String> formattedReservations = new ArrayList<>();

        for(Reservation reservation : sortedReservations) {
            formattedReservations.add(
                    String.format("ID: %2d || %s - %s || Guest: %-25s || Email: %s",
                            reservation.getId(),
                            reservation.getStart().toString(),
                            reservation.getEnd().toString(),
                            reservation.getGuest().getLastName() + " " + reservation.getGuest().getFirstName(),
                            reservation.getGuest().getEmail()
                            ));
        }

        return formattedReservations;
    }

    public Reservation makeReservation(Host host, Guest guest) {
        displayHeader(MainMenuOptions.MAKE.getHeader());

        Reservation reservation = new Reservation();
        reservation.setGuest(guest);
        reservation.setHost(host);
        reservation.setStart(consoleIO.readRequiredLocalDate("Start: "));
        reservation.setEnd(consoleIO.readRequiredLocalDate("End: "));
        reservation.setTotal();
        return reservation;
    }

    public int getReservationID() {
        return consoleIO.readInt("ID: ");
    }

    public Reservation editReservation(Reservation reservation) {
        if(reservation == null) {
            return null;
        }
        displayHeader(String.format("Editing Reservation %d", reservation.getId()));
        LocalDate start = consoleIO.readLocalDate("Start: ");
        LocalDate end = consoleIO.readLocalDate("End: ");

        if(start == null) {
            start = reservation.getStart();
        }
        if(end == null) {
            end = reservation.getEnd();
        }

        reservation.setStart(start);
        reservation.setEnd(end);
        reservation.setTotal();
        return reservation;
    }

    public boolean viewSummary(Reservation reservation) {
        consoleIO.printf("Start: %s\n", reservation.getStart().toString());
        consoleIO.printf("End: %s\n", reservation.getEnd().toString());
        consoleIO.printf("Total: %s\n", reservation.getTotal().toPlainString());
        return consoleIO.readBoolean("Confirm [y/n]: ");
    }

    public void displayHeader(String header) {
        consoleIO.println("");
        consoleIO.println(header);
        consoleIO.println("=".repeat(header.length()));
    }

    public void displayException(Exception ex) {
        displayHeader("A critical error occurred:");
        consoleIO.println(ex.getMessage());
    }

    public void displayStatus(boolean success, List<String> messages) {
        displayHeader(success ? "Success" : "Error");
        for (String message : messages) {
            consoleIO.println(message);
        }
    }

    public void enterToContinue() {
        consoleIO.readString("");
    }
}
