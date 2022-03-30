package learn.house.data;

import learn.house.models.Host;
import learn.house.models.Reservation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationRepositoryDouble implements ReservationRepository{

    private final List<Reservation> reservationList = new ArrayList<>();

    public ReservationRepositoryDouble() {
        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setGuest(GuestRepositoryDouble.GUEST);
        reservation.setHost(HostRepositoryDouble.HOST);
        reservation.setStart(LocalDate.of(2021, 3, 3));
        reservation.setEnd(LocalDate.of(2021, 3, 6));
        reservationList.add(reservation);
    }

    @Override
    public List<Reservation> findByHost(Host host) {
        return reservationList;
    }

    @Override
    public Reservation addReservation(Reservation reservation) throws DataException {
        reservation.setId(reservationList.size());
        reservationList.add(reservation);
        return reservation;
    }

    @Override
    public boolean editReservation(Reservation reservation) throws DataException {
        LocalDate start = reservationList.get(reservation.getId() - 1).getStart();
        reservationList.set(reservation.getId() - 1, reservation);
        return start != reservationList.get(reservation.getId() - 1).getStart();
    }

    @Override
    public boolean deleteReservation(Reservation reservation) throws DataException {
        int originalSize = reservationList.size();
        Reservation reservationRem = reservationList.remove(reservation.getId() - 1);
        return originalSize != reservationList.size();
    }

    @Override
    public Reservation findById(int id, Host host) {
        return reservationList.get(id);
    }

    @Override
    public int getTotalSize() {
        return reservationList.size();
    }
}
