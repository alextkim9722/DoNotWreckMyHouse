package learn.house.ui;

import learn.house.data.DataException;
import learn.house.domain.GuestService;
import learn.house.domain.HostService;
import learn.house.domain.ReservationService;
import learn.house.domain.Result;
import learn.house.models.Guest;
import learn.house.models.Host;
import learn.house.models.Reservation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Controller {
    private final ReservationService reservationService;
    private final GuestService guestService;
    private final HostService hostService;
    private final View view;

    public Controller(
            ReservationService reservationService, GuestService guestService, HostService hostService, View view) {
        this.reservationService = reservationService;
        this.guestService = guestService;
        this.hostService = hostService;
        this.view = view;
    }

    public void run() {
        view.displayHeader("Welcome to Do Not Wreck My House");
        try {
            runAppLoop();
        } catch (DataException ex) {
            view.displayException(ex);
        }
        view.displayHeader("Goodbye.");
    }

    private void runAppLoop() throws DataException {
        MainMenuOptions option;
        do {
            option = view.mainMenuSelection();
            switch (option) {
                case VIEW:
                    viewReservation();
                    break;
                case MAKE:
                    makeReservation();
                    break;
                case EDIT:
                    editReservation();
                    break;
                case DELETE:
                    deleteReservation();
                    break;
            }
        } while (option != MainMenuOptions.EXIT);
    }

    private void viewReservation(){
        view.displayHeader(MainMenuOptions.VIEW.getHeader());
        Result<Host> result = hostService.findByElement(view.getIdentifier("host"));

        if(!result.isSuccessful()) {
            view.displayStatus(result.isSuccessful(), result.getMessages());
        }else {
            Host host = result.getItem();
            List<Reservation> reservationList = reservationService.getReservations(host);
            view.viewReservations(view.formatReservations(reservationList));
        }

        view.enterToContinue();
    }

    private void makeReservation() throws DataException{
        view.displayHeader(MainMenuOptions.MAKE.getHeader());
        Reservation reservation;

        Result<Host> hostResult = hostService.findByElement(view.getIdentifier("host"));
        if(!hostResult.isSuccessful()) {
            view.displayStatus(hostResult.isSuccessful(), hostResult.getMessages());
        }else {

            Result<Guest> guestResult = guestService.findByElement(view.getIdentifier("guest"));
            if (!guestResult.isSuccessful()) {
                view.displayStatus(guestResult.isSuccessful(), guestResult.getMessages());
            }else {
                Host host = hostResult.getItem();
                Guest guest = guestResult.getItem();
                List<Reservation> reservationList = reservationService.getReservations(host);
                view.viewReservations(view.formatReservations(reservationList));
                reservation = view.makeReservation(host, guest);
                if(view.viewSummary(reservation)) {
                    Result<Reservation> result = reservationService.addReservation(reservation);
                    if(!result.isSuccessful()) {
                        view.displayStatus(result.isSuccessful(), result.getMessages());
                    }
                }
            }
        }
    }

    private void editReservation() throws DataException{
        view.displayHeader(MainMenuOptions.EDIT.getHeader());
        Result<Reservation> result = null;
        Reservation reservation;
        int id;

        Result<Host> hostResult = hostService.findByElement(view.getIdentifier("host"));
        if(!hostResult.isSuccessful()) {
            view.displayStatus(hostResult.isSuccessful(), hostResult.getMessages());
        }else {

            Result<Guest> guestResult = guestService.findByElement(view.getIdentifier("guest"));
            if (!guestResult.isSuccessful()) {
                view.displayStatus(guestResult.isSuccessful(), guestResult.getMessages());
            }else {
                Host host = hostResult.getItem();
                Guest guest = guestResult.getItem();

                List<Reservation> reservationList = reservationService.getReservationGuestList(host, guest);
                view.viewReservations(view.formatReservations(reservationList));

                id = view.getReservationID();
                result = reservationService.getReservation(id, host, reservationList);
                if(result.isSuccessful()) {
                    reservation = view.editReservation(result.getItem());
                    if(view.viewSummary(reservation)) {
                        result = reservationService.editReservation(reservation);
                    }
                }
            }

            if(!result.isSuccessful()) {
                view.displayStatus(result.isSuccessful(), result.getMessages());
            }
        }
    }

    private void deleteReservation() throws DataException{
        view.displayHeader(MainMenuOptions.DELETE.getHeader());
        Result<Reservation> result = null;
        int id;

        Result<Host> hostResult = hostService.findByElement(view.getIdentifier("host"));
        if(!hostResult.isSuccessful()) {
            view.displayStatus(hostResult.isSuccessful(), hostResult.getMessages());
        }else {

            Result<Guest> guestResult = guestService.findByElement(view.getIdentifier("guest"));
            if (!guestResult.isSuccessful()) {
                view.displayStatus(guestResult.isSuccessful(), guestResult.getMessages());
            }else {
                Host host = hostResult.getItem();
                Guest guest = guestResult.getItem();

                List<Reservation> reservationList = reservationService.getReservationGuestList(host, guest);
                view.viewReservations(view.formatReservations(reservationList));

                id = view.getReservationID();
                result = reservationService.getReservation(id, host, reservationList);
                if(result.isSuccessful()) {
                    result = reservationService.delete(result.getItem());
                }
            }
            if (!result.isSuccessful()) {
                view.displayStatus(result.isSuccessful(), result.getMessages());
            }
        }
    }
}
