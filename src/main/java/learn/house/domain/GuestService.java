package learn.house.domain;

import learn.house.data.GuestRepository;
import learn.house.models.Guest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class GuestService {
    private final GuestRepository guestRepository;

    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    public Result<Guest> findByElement(String element) {
        Result<Guest> result = new Result<>();

        if (element == null) {
            result.addErrorMessages("The element being searched for is empty.");
        }

        Guest guest = guestRepository.findByElement(element);

        if (guest == null) {
            result.addErrorMessages("No guest of that characteristic has been found.");
        }

        if (result.isSuccessful()) {
            result.setItem(guest);
        }

        return result;
    }

    public Result<Guest> findByElement(int id) {
        Result<Guest> result = new Result<>();

        Guest guest = guestRepository.findByElement(id);

        if (guest == null) {
            result.addErrorMessages("No guest of that characteristic has been found.");
        }

        if (result.isSuccessful()) {
            result.setItem(guest);
        }

        return result;
    }
}
