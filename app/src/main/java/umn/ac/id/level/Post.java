package umn.ac.id.level;

import java.io.Serializable;
import java.util.ArrayList;

public class Post implements Serializable {
    private String id, user, location, hotel, locationImg;
    private int travelDays, totalCost, ticketPrice, costPerNight;
    private boolean roundTrip;
    private ArrayList<Details> postDetails;

    public Post() {}

    public Post(String id, String user, String locationImg, String location, String hotel, int travelDays, int totalCost,
                int ticketPrice, int costPerNight, boolean roundTrip, ArrayList<Details> postDetails) {
        this.id = id;
        this.user = user;
        this.locationImg = locationImg;
        this.location = location;
        this.hotel = hotel;
        this.travelDays = travelDays;
        this.totalCost = totalCost;
        this.ticketPrice = ticketPrice;
        this.costPerNight = costPerNight;
        this.roundTrip = roundTrip;
        this.postDetails = postDetails;
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

    public String getLocationImg() {
        return locationImg;
    }

    public void setLocationImg(String locationImg) {
        this.locationImg = locationImg;
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

    public int getTravelDays() {
        return travelDays;
    }

    public void setTravelDays(int travelDays) {
        this.travelDays = travelDays;
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

    public boolean isRoundTrip() {
        return roundTrip;
    }

    public void setRoundTrip(boolean roundTrip) {
        this.roundTrip = roundTrip;
    }

    public ArrayList<Details> getPostDetails() {
        return postDetails;
    }

    public void setPostDetails(ArrayList<Details> postDetails) {
        this.postDetails = postDetails;
    }
}
