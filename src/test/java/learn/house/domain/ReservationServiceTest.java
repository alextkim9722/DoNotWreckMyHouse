package learn.house.domain;

import learn.house.data.*;
import learn.house.models.Reservation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class ReservationServiceTest {
    ReservationService reservationService = new ReservationService(
            new ReservationRepositoryDouble(),
            new GuestRepositoryDouble()
    );

    @Test
    public void shouldGetReservationsAndAddGuests() {
        List<Reservation> reservationList = reservationService.getReservations(HostRepositoryDouble.HOST);
        Assertions.assertEquals(1, reservationList.size());
        Assertions.assertEquals("asdf", reservationList.get(0).getGuest().getLastName());
    }

    @Test
    public void shouldAddReservations() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(2);
        reservation.setStart(LocalDate.of(2022, 3, 3));
        reservation.setEnd(LocalDate.of(2022, 3, 5));
        reservation.setHost(HostRepositoryDouble.HOST);
        reservation.setGuest(GuestRepositoryDouble.GUEST);
        reservation.setTotal();

        Result<Reservation> result = reservationService.addReservation(reservation);
        Assertions.assertTrue(result.isSuccessful());
    }

    @Test
    public void shouldEditReservations() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setStart(LocalDate.of(2022, 3, 3));
        reservation.setEnd(LocalDate.of(2022, 3, 5));
        reservation.setHost(HostRepositoryDouble.HOST);
        reservation.setGuest(GuestRepositoryDouble.GUEST);
        reservation.setTotal();

        Result<Reservation> result = reservationService.editReservation(reservation);
        Assertions.assertTrue(result.isSuccessful());
    }

    @Test
    public void shouldDeleteReservation() throws DataException {
        Result<Reservation> result = reservationService.delete(
                reservationService.getById(1, HostRepositoryDouble.HOST).getItem());
        Assertions.assertTrue(result.isSuccessful());
    }

    @Test
    public void shouldNotAddBecauseOfNulls() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(2);
        reservation.setStart(LocalDate.of(2022, 3, 3));
        reservation.setEnd(LocalDate.of(2022, 3, 5));
        reservation.setHost(HostRepositoryDouble.HOST);
        reservation.setTotal();

        Result<Reservation> result = reservationService.addReservation(reservation);
        Assertions.assertFalse(result.isSuccessful());
    }

    @Test
    public void shouldNotAddBecauseOfOverlap() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(2);
        reservation.setStart(LocalDate.of(2021, 3, 4));
        reservation.setEnd(LocalDate.of(2021, 3, 7));
        reservation.setHost(HostRepositoryDouble.HOST);
        reservation.setGuest(GuestRepositoryDouble.GUEST);
        reservation.setTotal();

        Result<Reservation> result = reservationService.addReservation(reservation);
        Assertions.assertFalse(result.isSuccessful());
    }

    @Test
    public void shouldNotAddBecauseOfStart() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(2);
        reservation.setStart(LocalDate.of(2021, 3, 8));
        reservation.setEnd(LocalDate.of(2021, 3, 6));
        reservation.setHost(HostRepositoryDouble.HOST);
        reservation.setGuest(GuestRepositoryDouble.GUEST);
        reservation.setTotal();

        Result<Reservation> result = reservationService.addReservation(reservation);
        Assertions.assertFalse(result.isSuccessful());
    }
}
