package learn.house.data;

import learn.house.models.Host;
import learn.house.models.Reservation;

import java.util.List;

public interface ReservationRepository {
    List<Reservation> findByHost(Host host);
    Reservation addReservation(Reservation reservation) throws DataException;
    boolean editReservation(Reservation reservation) throws DataException;
    boolean deleteReservation(Reservation reservation) throws DataException;
    Reservation findById(int id, Host host);
    int getTotalSize();
}