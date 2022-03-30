package learn.house.data;

import learn.house.models.Host;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class HostFileRepository implements HostRepository{
    private static final String DELIMITER = ",";
    private final String filePath;
    private int totalSize;

    // public HostFileRepository(@Value("${hostFilePath}") String filePath) {this.filePath = filePath;}

    public HostFileRepository(String filePath) {this.filePath = filePath;}

    @Override
    public List<Host> findAll() {
        List<Host> result = new ArrayList<>();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            bufferedReader.readLine();

            for(String line = bufferedReader.readLine();line!= null;line = bufferedReader.readLine()) {
                String[] data = line.split(DELIMITER, -1);
                if(data.length == 10) {
                    result.add(deserialize(data));
                }
            }

        } catch(IOException e) {}

        totalSize = result.size();

        return result;
    }

    @Override
    public Host findByElement(String element) {
        List<Host> hostList = findAll();
        Host targetHost = null;

        for(Host host : hostList) {
            if(element.equalsIgnoreCase(host.getEmail())
            || element.equalsIgnoreCase(host.getId())
            || element.equalsIgnoreCase(host.getPhoneNumber())) {
                targetHost = host;
            }
        }

        return targetHost;
    }

    @Override
    public int getTotalSize() { return totalSize; }

    private Host deserialize(String[] data) {
        Host host = new Host();
        host.setId(data[0]);
        host.setLastName(data[1]);
        host.setEmail(data[2]);
        host.setPhoneNumber(data[3]);
        host.setAddress(data[4]);
        host.setCity(data[5]);
        host.setState(data[6]);
        host.setPostal(Integer.parseInt(data[7]));
        host.setStandardRate(new BigDecimal(Double.parseDouble(data[8])));
        host.setWeeklyRate(new BigDecimal(Double.parseDouble(data[9])));
        return host;
    }
}
