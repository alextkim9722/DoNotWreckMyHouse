package learn.house.data;

import learn.house.models.Guest;
import learn.house.models.Host;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HostRepositoryDouble implements HostRepository{

    public final static Host HOST = makeHost();

    private List<Host> hostList = new ArrayList<>();

    public HostRepositoryDouble() {hostList.add(makeHost());}

    @Override
    public List<Host> findAll() {
        return hostList;
    }

    @Override
    public Host findByElement(String element) {
        for(Host host : hostList) {
            if(host.getEmail().equals(element) || host.getPhoneNumber().equals(element) ||
            host.getId().equals(element)) {
                return host;
            }
        }

        return null;
    }

    @Override
    public int getTotalSize() {
        return 0;
    }

    private static Host makeHost() {
        Host host = new Host();
        host.setId("Host");
        host.setWeeklyRate(new BigDecimal(0.2));
        host.setStandardRate(new BigDecimal(0.4));
        host.setPhoneNumber("12364567890");
        host.setEmail("1234@567890.com");
        host.setLastName("Bob");
        host.setAddress("Burly Court");
        host.setCity("Turse Town");
        host.setState("TX");
        host.setPostal(12345);
        return host;
    }
}
