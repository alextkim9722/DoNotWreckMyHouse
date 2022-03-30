package learn.house.data;

import learn.house.models.Guest;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GuestFileRepository implements GuestRepository{
    private static final String DELIMITER = ",";
    private final String filePath;
    private int totalSize;

    // public GuestFileRepository(@Value("${guestFilePath}") String filePath) {this.filePath = filePath;}

    public GuestFileRepository(String filePath) {this.filePath = filePath;}

    @Override
    public List<Guest> findAll() {
        List<Guest> result = new ArrayList<>();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            bufferedReader.readLine();

            for(String line = bufferedReader.readLine();line!= null;line = bufferedReader.readLine()) {
                String[] data = line.split(DELIMITER, -1);
                if(data.length == 6) {
                    result.add(deserialize(data));
                }
            }

        } catch(IOException e) {}

        totalSize = result.size();

        return result;
    }

    @Override
    public Guest findByElement(String element) {
        List<Guest> guestList = findAll();
        Guest targetGuest = null;

        for(Guest guest : guestList) {
            if(element.equalsIgnoreCase(guest.getEmail())
                    || element.equalsIgnoreCase(guest.getPhoneNumber())) {
                targetGuest = guest;
            }
        }

        return targetGuest;
    }

    @Override
    public Guest findByElement(int id) {
        List<Guest> guestList = findAll();
        Guest targetGuest = null;

        for(Guest guest : guestList) {
            if(guest.getId() == id) {
                targetGuest = guest;
            }
        }

        return targetGuest;
    }

    @Override
    public int getTotalSize() { return totalSize; }

    private Guest deserialize(String[] data) {
        Guest guest = new Guest();
        guest.setId(Integer.parseInt(data[0]));
        guest.setFirstName(data[1]);
        guest.setLastName(data[2]);
        guest.setEmail(data[3]);
        guest.setPhoneNumber(data[4]);
        guest.setState(data[5]);
        return guest;
    }
}
