package learn.house.data;

import learn.house.models.Guest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class GuestFileRepositoryTest {
    static final String SEED_PATH = "./data/test/guestSeed.csv";
    static final String TEST_PATH = "./data/test/guestTest.csv";

    GuestRepository guestFileRepository = new GuestFileRepository(TEST_PATH);

    @BeforeEach
    public void setup() throws IOException {
        Path seedPath = Paths.get(SEED_PATH);
        Path testPath = Paths.get(TEST_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    public void shouldFindAll() {
        Assertions.assertEquals(5, guestFileRepository.findAll().size());
    }

    @Test
    public void shouldFindByPhoneNumber() {
        Guest guest = guestFileRepository.findByElement("(313) 2245034");
        Assertions.assertEquals(guest.getId(), 3);
    }

    @Test
    public void shouldFindByEmail() {
        Guest guest = guestFileRepository.findByElement("tcarncross2@japanpost.jp");
        Assertions.assertEquals(guest.getId(), 3);
    }

    @Test
    public void shouldFindByID() {
        Guest guest = guestFileRepository.findByElement(3);
        Assertions.assertEquals(guest.getId(), 3);
    }
}
