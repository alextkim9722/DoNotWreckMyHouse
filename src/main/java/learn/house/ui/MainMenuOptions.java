package learn.house.ui;

public enum MainMenuOptions {

    EXIT(0, "Exit"),
    VIEW(1, "View Reservations for Host"),
    MAKE(2, "Make a Reservation"),
    EDIT(3, "Edit a Reservation"),
    DELETE(4, "Remove a Reservation");


    private int value;
    private String header;

    MainMenuOptions(int value, String header) {
        this.value = value;
        this.header = header;
    }

    public static MainMenuOptions fromValue(int value) {
        for (MainMenuOptions option : MainMenuOptions.values()) {
            if (option.getValue() == value) {
                return option;
            }
        }
        return EXIT;
    }

    public int getValue() {
        return value;
    }

    public String getHeader() {
        return header;
    }
}
