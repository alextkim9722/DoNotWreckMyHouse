package learn.house.domain;

import learn.house.data.DataException;
import learn.house.data.GuestRepository;
import learn.house.data.ReservationRepository;
import learn.house.models.Host;
import learn.house.models.Reservation;

import java.util.List;

public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final GuestRepository guestRepository;

    public ReservationService(ReservationRepository reservationsRepository, GuestRepository guestRepository) {
        this.reservationRepository = reservationsRepository;
        this.guestRepository = guestRepository;
    }

    public Result<Reservation> addReservation(Reservation reservation) throws DataException {
        Result<Reservation> result = validations(reservation);
        if(!result.isSuccessful()) {
            return result;
        }

        if(reservationRepository.addReservation(reservation) == null) {
            result.addErrorMessages("Failed to add the reservation.");
            return result;
        }
        result.setItem(reservation);
        return result;
    }

    public Result<Reservation> editReservation(Reservation reservation) throws DataException{
        Result<Reservation> result = validations(reservation);
        if(!result.isSuccessful()) {
            return result;
        }

        result = getById(reservation.getId(), reservation.getHost());
        if(!result.isSuccessful()) {
            return result;
        }

        if(!reservationRepository.editReservation(reservation)) {
            result.addErrorMessages("Failed to edit the reservation.");
            return result;
        }

        result.setItem(reservation);
        return result;
    }

    public List<Reservation> getReservations(Host host) {
        List<Reservation> reservationList = reservationRepository.findByHost(host);

        for(Reservation reservation : reservationList) {
            reservation.setGuest(guestRepository.findByElement(reservation.getGuest().getId()));
        }

        return reservationList;
    }

    public Result<Reservation> delete(Reservation reservation) throws DataException{
        Result<Reservation> result = new Result<>();

        if(!reservationRepository.deleteReservation(reservation)) {
            result.addErrorMessages("Failed to delete the reservation.");
        }
        return result;
    }

    public Result<Reservation> getById(int id, Host host) {
        Result<Reservation> result = new Result<>();
        int formattedId = id - 1;

        if(formattedId > reservationRepository.getTotalSize() - 1 || formattedId < 0) {
            result.addErrorMessages("The id is out of bounds, please enter a valid id.");
        } else {
            result.setItem(reservationRepository.findById(formattedId, host));
        }

        if(result.getItem() == null) {
            result.addErrorMessages("The reservation you are looking for does not exist.");
        }

        return result;
    }

    private Result<Reservation> validations(Reservation reservation) {
        Result<Reservation> result;

        result = validateNulls(reservation);
        if(!result.isSuccessful()) {
            return result;
        }

        result = validateOverlap(reservation);
        if(!result.isSuccessful()){
            return result;
        }

        result = validateStart(reservation);
        if(!result.isSuccessful()) {
            return result;
        }

        return result;
    }

    private Result<Reservation> validateNulls(Reservation reservation) {
        Result<Reservation> result = new Result<>();

        if(reservation.getHost() == null) {
            result.addErrorMessages("The reservation host is empty.");
        }
        if(reservation.getStart() == null) {
            result.addErrorMessages("The reservation start is empty.");
        }
        if(reservation.getEnd() == null) {
            result.addErrorMessages("The reservation end is empty.");
        }
        if(reservation.getTotal() == null) {
            result.addErrorMessages("The reservation total is empty.");
        }
        if(reservation.getGuest() == null) {
            result.addErrorMessages("The reservation guest is empty.");
        }

        return result;
    }

    private Result<Reservation> validateOverlap(Reservation reservation) {
        List<Reservation> listReservation = reservationRepository.findByHost(reservation.getHost());
        Result<Reservation> result = new Result<>();

        for (Reservation listedReservation : listReservation) {
            if(reservation.getId() != listedReservation.getId()) {
                if ((reservation.getStart().isAfter(listedReservation.getStart())
                        && reservation.getStart().isBefore(listedReservation.getEnd())) ||
                        (reservation.getEnd().isAfter(listedReservation.getStart())
                                && reservation.getEnd().isBefore(listedReservation.getEnd()))) {
                    result.addErrorMessages("Reservations overlap with each other.");
                }
            }
        }

        return result;
    }

    private Result<Reservation> validateStart(Reservation reservation) {
        Result<Reservation> result = new Result<>();

        if(!reservation.getStart().isBefore(reservation.getEnd())) {
            result.addErrorMessages("The start is not before the end.");
        }

        return result;
    }
}
