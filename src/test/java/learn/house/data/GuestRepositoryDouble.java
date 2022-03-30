package learn.house.data;

import learn.house.models.Guest;

import java.util.ArrayList;
import java.util.List;

public class GuestRepositoryDouble implements GuestRepository{
    public static final Guest GUEST = makeGuest();

    private List<Guest> guestList = new ArrayList<>();

    public GuestRepositoryDouble() {
        guestList.add(GUEST);
    }

    @Override
    public List<Guest> findAll() {
        return guestList;
    }

    @Override
    public Guest findByElement(String element) {
        return null;
    }

    @Override
    public Guest findByElement(int id) {
        return guestList.get(id);
    }

    @Override
    public int getTotalSize() {
        return 0;
    }

    private static Guest makeGuest() {
        Guest guest = new Guest();
        guest.setId(0);
        guest.setLastName("asdf");
        guest.setFirstName("hjkl");
        guest.setEmail("asdf@hjkl.com");
        guest.setPhoneNumber("1234567890");
        guest.setState("GA");
        return guest;
    }
}
