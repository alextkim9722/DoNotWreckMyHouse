package learn.house.domain;

import java.util.ArrayList;
import java.util.List;

public class Response {
    private List<String> messages = new ArrayList<>();
    public boolean isSuccessful() { return messages.size() == 0; }
    public List<String> getMessages() { return new ArrayList<>(messages); }
    public void addErrorMessages(String msg) {messages.add(msg);}
}
