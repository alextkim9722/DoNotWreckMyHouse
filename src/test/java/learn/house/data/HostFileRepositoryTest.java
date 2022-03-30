package learn.house.data;

import learn.house.models.Host;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class HostFileRepositoryTest {
    static final String SEED_PATH = "./data/test/hostSeed.csv";
    static final String TEST_PATH = "./data/test/hostTest.csv";

    HostFileRepository hostFileRepository = new HostFileRepository(TEST_PATH);

    @BeforeEach
    public void setup() throws IOException {
        Path seedPath = Paths.get(SEED_PATH);
        Path testPath = Paths.get(TEST_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    public void shouldFindAll() {
        Assertions.assertEquals(7, hostFileRepository.findAll().size());
    }

    @Test
    public void shouldFindByPhoneNumber() {
        Host host = hostFileRepository.findByElement("(214) 5201692");
        Assertions.assertEquals(host.getId(), "9f2578e7-6723-482b-97c1-f9be0b7c96dd");
    }

    @Test
    public void shouldFindByEmail() {
        Host host = hostFileRepository.findByElement("rspellesy3@google.co.jp");
        Assertions.assertEquals(host.getId(), "9f2578e7-6723-482b-97c1-f9be0b7c96dd");
    }

    @Test
    public void shouldFindByID() {
        Host host = hostFileRepository.findByElement("9f2578e7-6723-482b-97c1-f9be0b7c96dd");
        Assertions.assertEquals(host.getId(), "9f2578e7-6723-482b-97c1-f9be0b7c96dd");
    }
}
