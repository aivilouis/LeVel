package umn.ac.id.level;

public class Post {
    private String id, user, location, hotel;
    private int days, totalCost, ticketPrice, costPerNight;

    public Post() {}

    public Post(String id, String user, String location, String hotel, int days, int totalCost, int ticketPrice, int costPerNight) {
        this.id = id;
        this.user = user;
        this.location = location;
        this.hotel = hotel;
        this.days = days;
        this.totalCost = totalCost;
        this.ticketPrice = ticketPrice;
        this.costPerNight = costPerNight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public int getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(int ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public int getCostPerNight() {
        return costPerNight;
    }

    public void setCostPerNight(int costPerNight) {
        this.costPerNight = costPerNight;
    }
}
