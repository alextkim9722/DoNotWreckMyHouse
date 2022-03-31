package learn.house.domain;

import learn.house.data.HostRepository;
import learn.house.models.Host;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class HostService {
    private final HostRepository hostRepository;

    public HostService(HostRepository hostRepository) {
        this.hostRepository = hostRepository;
    }

    public Result<Host> findByElement(String element) {
        Result<Host> result = new Result<>();

        if (element == null) {
            result.addErrorMessages("The element being searched for is empty.");
        }

        Host host = hostRepository.findByElement(element);

        if (host == null) {
            result.addErrorMessages("No host of that characteristic has been found.");
        }

        if (result.isSuccessful()) {
            result.setItem(host);
        }

        return result;
    }
}
