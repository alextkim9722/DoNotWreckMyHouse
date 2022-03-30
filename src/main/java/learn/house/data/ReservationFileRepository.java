package learn.house.data;

import learn.house.models.Guest;
import learn.house.models.Host;
import learn.house.models.Reservation;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationFileRepository implements ReservationRepository{

    private static final String HEADER = "id,start_date,end_date,guest_id,total";
    private static final String DELIMITER = ",";
    private final String directory;
    private int totalSize;

    /*
    public ReservationFileRepository(@Value("${reservationFilePath}") String directory) {
        this.directory = directory;
    }
     */

    public ReservationFileRepository(String directory) {
        this.directory = directory;
    }

    @Override
    public List<Reservation> findByHost(Host host) {
        List<Reservation> reservationList = new ArrayList<>();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(createFilePath(host)))) {
            bufferedReader.readLine();

            for(String line = bufferedReader.readLine();line != null;line = bufferedReader.readLine()) {
                String[] data = line.split(DELIMITER);

                if(data.length == 5) {
                    reservationList.add(deserialize(data));
                    reservationList.get(reservationList.size() - 1).setHost(host);
                    reservationList.get(reservationList.size() - 1).setTotal();
                }
            }

        }catch(IOException e) {}

        totalSize = reservationList.size();

        return reservationList;
    }

    @Override
    public Reservation addReservation(Reservation reservation) throws DataException{
        List<Reservation> reservationList = findByHost(reservation.getHost());
        reservationList.add(reservation);
        reservationList.get(reservationList.size() - 1).setId(reservationList.size());
        writeAll(reservationList, reservation.getHost());
        return reservation;
    }

    @Override
    public boolean editReservation(Reservation reservation) throws DataException {
        List<Reservation> reservationList = findByHost(reservation.getHost());
        Reservation targetReservation = findById(reservation.getId(), reservation.getHost());

        if (targetReservation != null) {
            reservation.setId(targetReservation.getId());
            reservationList.set(targetReservation.getId() - 1, reservation);
            writeAll(reservationList, reservation.getHost());
            return true;
        }

        return false;
    }

    @Override
    public boolean deleteReservation(Reservation reservation) throws DataException{
        List<Reservation> reservationList = findByHost(reservation.getHost());
        Reservation removedReservation = reservationList.remove(reservation.getId() - 1);
        for(int i = 0;i < reservationList.size();i++) {
            reservationList.get(i).setId(i + 1);
        }
        writeAll(reservationList, reservation.getHost());

        return removedReservation != null;
    }

    @Override
    public Reservation findById(int id, Host host) {
        List<Reservation> reservationList = findByHost(host);
        Reservation targetReservation = null;

        for(Reservation reservation : reservationList) {
            if(id == reservation.getId()) {
                targetReservation = reservation;
            }
        }
        return targetReservation;
    }

    @Override
    public int getTotalSize() { return totalSize; }

    private String createFilePath(Host host) { return String.format("%s/%s.csv", directory, host.getId()); }

    private void writeAll(List<Reservation> reservationList, Host host) throws DataException{
        try(PrintWriter printWriter = new PrintWriter(createFilePath(host))) {
            printWriter.println(HEADER);

            for(Reservation reservation : reservationList) {
                printWriter.write(serialize(reservation));
            }
        } catch(IOException e) {
            throw new DataException(e);
        }
    }

    private String serialize(Reservation reservation) {
        return String.format(
                "%s,%s,%s,%s,%s\n",
                reservation.getId(),
                reservation.getStart(),
                reservation.getEnd(),
                reservation.getGuest().getId(),
                reservation.getTotal()
                );
    }

    private Reservation deserialize(String[] data) {
        Reservation reservation = new Reservation();
        Guest guest = new Guest();
        reservation.setId(Integer.parseInt(data[0]));
        reservation.setStart(LocalDate.parse(data[1]));
        reservation.setEnd(LocalDate.parse(data[2]));
        guest.setId(Integer.parseInt(data[3]));
        reservation.setGuest(guest);
        return reservation;
    }
}
