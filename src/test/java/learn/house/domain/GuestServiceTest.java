package learn.house.domain;

import learn.house.data.GuestRepositoryDouble;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GuestServiceTest {
    GuestService guestService = new GuestService(new GuestRepositoryDouble());

    @Test
    public void shouldFindByElement(){
        Assertions.assertEquals(guestService.findByElement("asdf@hjkl.com").getItem().getLastName(),
                "asdf");
    }

    @Test
    public void shouldFindByIntElement() {
        Assertions.assertEquals(guestService.findByElement(0).getItem().getLastName(),
                "asdf");
    }
}
