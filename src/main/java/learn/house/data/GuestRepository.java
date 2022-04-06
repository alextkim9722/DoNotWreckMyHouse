package learn.house.data;

import learn.house.models.Guest;

import java.util.List;

public interface GuestRepository {
    // guest_id,first_name,last_name,email,phone,state

    List<Guest> findAll();
    Guest findByElement(String element);
    Guest findByElement(int id);
    int getTotalSize();
}
