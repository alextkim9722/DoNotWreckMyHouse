package learn.house.data;

import learn.house.models.Host;
import java.util.List;

public interface HostRepository {
    List<Host> findAll();
    Host findByElement(String element);
    int getTotalSize();
}
