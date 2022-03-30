package learn.house.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.Calendar;

public class Reservation {
    private Host host;
    private Guest guest;
    private int id;
    private LocalDate start;
    private LocalDate end;
    private BigDecimal total;

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal() {
        if (host == null || host.getStandardRate() == null || host.getWeeklyRate() == null) {
            total = BigDecimal.ZERO;
        } else {
            BigDecimal temp = new BigDecimal(0);

            for(LocalDate beg = start;beg.isBefore(end);beg = beg.plusDays(1)) {
                if(beg.get(ChronoField.DAY_OF_WEEK) == Calendar.SATURDAY ||
                        beg.get(ChronoField.DAY_OF_WEEK) == Calendar.SUNDAY) {
                    temp.add(host.getWeeklyRate());
                } else {
                    temp.add(host.getStandardRate());
                }
            }

            total = temp;
        }
    }
}
