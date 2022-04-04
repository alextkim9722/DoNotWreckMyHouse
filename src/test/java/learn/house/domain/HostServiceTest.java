package learn.house.domain;

import learn.house.data.HostRepositoryDouble;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HostServiceTest {
    HostService hostService = new HostService(new HostRepositoryDouble());

    @Test
    public void shouldFindByEmail(){
        Assertions.assertEquals(hostService.findByElement("1234@567890.com").getItem().getLastName(),
                "Bob");
    }

    @Test
    public void shouldFindById(){
        Assertions.assertEquals(hostService.findByElement("Host").getItem().getLastName(),
                "Bob");
    }

    @Test
    public void shouldFindByPhone(){
        Assertions.assertEquals(hostService.findByElement("12364567890").getItem().getLastName(),
                "Bob");
    }
}
