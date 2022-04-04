package learn.house.data;

import learn.house.models.Guest;
import learn.house.models.Host;

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
        for(Guest guest : guestList) {
            if(guest.getEmail().equals(element) || guest.getPhoneNumber().equals(element)) {
                return guest;
            }
        }

        return null;
    }

    @Override
    public Guest findByElement(int id) {
        return guestList.get(id);
    }

    @Override
    public int getTotalSize() {
        return guestList.size();
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
