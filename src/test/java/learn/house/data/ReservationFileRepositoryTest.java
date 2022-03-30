package learn.house.data;

import learn.house.models.Guest;
import learn.house.models.Host;
import learn.house.models.Reservation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

public class ReservationFileRepositoryTest {
    static final String SEED_PATH = "./data/test/reservationSeed.csv";
    static final String TEST_PATH = "reservationTest.csv";
    static final String TEST_DIRECTORY = "./data/test";

    ReservationFileRepository reservationFileRepository = new ReservationFileRepository(TEST_DIRECTORY);

    @BeforeEach
    public void setup() throws IOException {
        Path seedPath = Paths.get(SEED_PATH);
        Path testPath = Paths.get(TEST_DIRECTORY + "/" + TEST_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    public void shouldFindByHost() {
        Host host = new Host();
        host.setId("reservationTest");
        Assertions.assertEquals(5, reservationFileRepository.findByHost(host).size());
    }

    @Test
    public void shouldHaveHost() {
        Host host = new Host();
        host.setId("reservationTest");
        Assertions.assertEquals("reservationTest",
                reservationFileRepository.findByHost(host).get(0).getHost().getId());
    }

    @Test
    public void shouldAddReservation() throws DataException {
        Host host = new Host();
        host.setId("reservationTest");

        Guest guest = new Guest();
        guest.setId(1);

        Reservation reservation = new Reservation();
        reservation.setHost(host);
        reservation.setGuest(guest);

        Assertions.assertEquals(6, reservationFileRepository.addReservation(reservation).getId());
    }

    @Test
    public void shouldEditReservation() throws DataException {
        LocalDate now = LocalDate.now();

        Host host = new Host();
        host.setId("reservationTest");

        Guest guest = new Guest();
        guest.setId(1);

        Reservation reservation = new Reservation();
        reservation.setId(3);
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setStart(now);
        reservation.setEnd(now);
        Assertions.assertTrue(reservationFileRepository.editReservation(reservation));

        List<Reservation> reservationList = reservationFileRepository.findByHost(host);
        Assertions.assertEquals(now, reservationList.get(2).getStart());
    }

    @Test
    public void shouldDeleteReservation() throws DataException {
        Host host = new Host();
        host.setId("reservationTest");

        Reservation reservation = new Reservation();
        reservation.setId(2);
        reservation.setHost(host);

        Assertions.assertTrue(reservationFileRepository.deleteReservation(reservation));

        List<Reservation> reservationList = reservationFileRepository.findByHost(host);
        Assertions.assertEquals(4, reservationList.get(reservationList.size() - 1).getId());
    }
}
