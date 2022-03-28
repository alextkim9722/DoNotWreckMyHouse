# Requirements
## High Level Requirements
* [ ] Administrator may view existing reservations for hosts.
* [ ] Administrator may create a reservation for a guest with a host.
* [ ] Administrator may edit existing reservations.
* [ ] Administrator may cancel a future reservation.

## Run-Time Requirements
### View Reservations for Hosts
* [ ] Display all reservations for a single host.
* [ ] Users can pick from a list of hosts or...
* [ ] ...users can use a unique identifier(name, address, etc) to find host.
* [ ] If no reservations are found for a host, print an error message.
* [ ] Show guests, date, totals, etc. for each reservation.
* [ ] Sort reservations by date

### Making a Reservation
* [ ] Users can pick from a list of guests or...
* [ ] ...users can use a unique identifier(name, address, etc) to find guest.
* [ ] Users can pick from a list of hosts or...
* [ ] ...users can use a unique identifier(name, address, etc) to find host.
* [ ] Show all reservations so the user can pick an empty one.
* [ ] Enter the start and end date for a reservation.
* [ ] Guest, host, start, and end are all required.
* [ ] Guests and hosts must already exist in the database.
* [ ] Start must come before the end.
* [ ] Reservations must not overlap.
* [ ] Start must be in the future.

### Canceling a Reservation
* [ ] Find a reservation using the host and id or...
* [ ] users can pick from a list.
* [ ] Only future reservations are shown.
* [ ] On success, display a message.
* [ ] Cannot cancel past reservations.

## Technical Requirements
* [ ] Must be a maven project.
* [ ] Spring annotation dependency injection.
* [ ] Financial math must be done through BigDecimal.
* [ ] Dates must be LocalDate and not Strings.
* [ ] All file data must have appropriate models.
* [ ] Reservation identifiers are unique to hosts.
* [ ] Reservation-Host identifiers must be unique across applications.

# Planning
## Class Structure
```
src
├───main
│   ├───java
│   │   └───learn
│   │       └───house
│   │           │   App.java                            -- app entry point
│   │           │
│   │           ├───data
│   │           │       DataException.java              -- data layer custom exception
│   │           │       HostsFileRepository.java        -- concrete hosts repository
│   │           │       GuestsFileRepository.java       -- concrete guests repository
│   │           │       ReservationsFileRepository.java -- concrete reservations repository
│   │           │       HostsRepository.java            -- hosts repository interface
│   │           │       GuestsRepository.java           -- guests repository interface
│   │           │       ReservationsRepository.java     -- reservations repository interface
│   │           │
│   │           ├───domain
│   │           │       Response.java                   -- interface for handingling successes
│   │           │       Result.java                     -- domain result for handling success and grabbing objects
│   │           │       HostService.java                -- used to handle the host repository
│   │           │       GuestService.java               -- used to handle the guest repository
│   │           │       ReservationService.java         -- used to handle the reservation, host, and guest repository
│   │           │
│   │           ├───models
│   │           │       Host.java                       -- data model for the host service
│   │           │       Guest.java                      -- data model for the guest service
│   │           │       Reservations.java               -- data model for the reservation service
│   │           │
│   │           └───ui
│   │                   Controller.java                 -- UI controller
│   │                   View.java                       -- all menu printings for UI
│   │                   ConsoleIO.java                  -- used to handle printing methods
│   │                   MainMenuOptions.java            -- enum that handles all the main menu selections
│   └───resources
│           data.properties                             -- used to store file paths
│
└───test
    └───java
        └───learn
            └───solar
                ├───data
                └───domain
```

## Pseudocode
### What is Considered Unique?
* Phone number
* Email address
* UID

### [0] View Reservation
1. [ ] Print title screen
2. [ ] Ask the user if they want to choose from a list or...
   1. [ ] Use a for loop to print out a list of hosts.
   2. [ ] Users can pick from the list number.
   3. [ ] Grab the host at the list number and get their UID --> 1
   4. [ ] Find and return host using UID
3. [ ] Use a unique identifier
   1. [ ] Grab the host at the list number and get their UID --> 1
   2. [ ] Find and return host using UID
4. [ ] Find a list of reservations based on the UID --> 2
5. [ ] Loop through the list and print out the reservation

### [1] Finding Host Using UID
1. [ ] Grab all hosts from the repository.
2. [ ] Loop through each host element in the list.
3. [ ] If the host element has the target UID
   1. [ ] return a result with the host

### [2] Finding Reservations using UID
1. [ ] Create filepath using the format: "{UID}.csv"
2. [ ] Load repository using the filepath.
3. [ ] Deserialize each line and place it into a list.
4. [ ] For each list's guest id, set the reservation guest variable to a guest model
5. [ ] Return a result with the list of reservations.

### [3] Making a Reservation
1. [ ] Ask the user for the guest's identifier.
   1. [ ] May use a list or some unique identifier
2. [ ] Ask the user for the host's identifier.
   1. [ ] May use a list or some unique identifier
3. [ ] Print out a list of reservations that the host has.
   1. [ ] This list will show those that are available.
   2. [ ] Also shows times that are taken.
4. [ ] Enter the start date and end date for reservation
5. [ ] Validate that this reservation is free.
   1. [ ] Check that guest, host, start, and end are not null.
   2. [ ] Check that guests and host exist in the database.
   3. [ ] Check that start comes before the end.
   4. [ ] Check that there is no overlap.
   5. [ ] Check that the start time is in the future.
6. [ ] Print a recap with reservation information/pricing.
7. [ ] Ask for confirmation

### [4] Editing a Reservation
1. [ ] Ask the user for the guest's identifier.
   1. [ ] May use a list or some unique identifier
2. [ ] Ask the user for the host's identifier.
   1. [ ] May use a list or some unique identifier
3. [ ] Show a list of reservations that are taken.
4. [ ] Ask for the id of the reservation.
5. [ ] Grab the reservation.
   1. Ask for the new start.
   2. Ask for the new end.
6. [ ] Validate that this reservation is free.
   3. [ ] Check that guest, host, start, and end are not null.
   4. [ ] Check that guests and host exist in the database.
   5. [ ] Check that start comes before the end.
   6. [ ] Check that there is no overlap.
   7. [ ] Check that the start time is in the future.
7. [ ] Print a recap with reservation information/pricing.
8. [ ] Ask for confirmation

## Class Methods
### Data
#### HostsRepository_ - INTERFACE
##### Methods:
* [ ] public List<Host> findAll()
* [ ] public Host findByElement(String)

#### GuestsRepository_ - INTERFACE
##### Methods:
* [ ] public List<Guest> findAll()
* [ ] public Host findByElement(String)

#### ReservationsRepository_ - INTERFACE
##### Methods:
* [ ] public List<Reservations> findByHost()
* [ ] public Reservation addReservation(Reservation)
* [ ] public boolean deleteReservation(Reservation)
* [ ] public Reservation findById(Int)

#### HostsFileRepository_
##### Methods:
* [ ] public List<Host> findAll()
* [ ] public Host findByElement(String)
* [ ] public Forager deserialize()

#### GuestsFileRepository_
##### Methods:
* [ ] public List<Guest> findAll()
* [ ] public Host findByElement(String)
* [ ] public Guest deserialize()

#### ReservationsFileRepository_
##### Methods:
* [ ] public List<Reservations> findByHost()
* [ ] public Reservation addReservation(Reservation)
* [ ] public boolean deleteReservation(Reservation)
* [ ] public Reservation findById(Int)
* [ ] public String getHostReservations(String)
* [ ] public void writeAll(List<Reservations>)
* [ ] public String serialize(Reservation)
* [ ] public Reservation deserialize(String[])

### Domain
#### Response_
##### Methods:
* [ ] public boolean isSuccess()
* [ ] public List<Strings> getMessages()
* [ ] public void addMessage(String)

#### Result_
##### Methods:
* [ ] public T getPayload()
* [ ] public void setPayload(T)

#### HostService_
##### Methods:
* [ ] public HostService(HostRepository)
* [ ] public Host findByElement(String)
* [ ] public String getID(Host)

#### GuestService_
##### Methods:
* [ ] public GuestService(GuestRepository)
* [ ] public Guest findByElement(String)

#### ReservationService_
##### Methods:
* [ ] public ReservationService(ReservationsRepository)
* [ ] public Result<Reservation> add(Reservation)
* [ ] public List<Reservation> getReservations(String)
* [ ] public Result<Reservation> getById(int)
* [ ] public Result<Reservation> validations()
* [ ] public Result<Reservation> validateNulls()
* [ ] public Result<Reservation> validateOverlap()
* [ ] public Result<Reservation> validateStart()

### Model
#### Host_
##### Variables:
* [ ] public String id
* [ ] public String lastName
* [ ] public String firstName
* [ ] public String email
* [ ] public String phoneNumber
* [ ] public String address
* [ ] public BigDecimal standardRate
* [ ] public BigDecimal weeklyRate
##### Methods:
* [ ] getters and setters

#### Guest_
##### Variables:
* [ ] public int id
* [ ] public String lastName
* [ ] public String firstName
* [ ] public String email
* [ ] public String phoneNumber
* [ ] public String address
##### Methods:
* [ ] getters and setters

#### Reservation_
##### Variables:
* [ ] public Host host
* [ ] public Guest guest
* [ ] public int id
* [ ] public LocalDate start
* [ ] public LocalDate end
* [ ] public BigDecimal total
##### Methods:
* [ ] getters and setters
* [ ] public void calculateTotal()

