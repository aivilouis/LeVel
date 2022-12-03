package umn.ac.id.level;

import java.io.Serializable;

public class UserPosts implements Serializable {

    private String location, hotel;
    private int days, totalcost, ticketprice, costpernight;

    public UserPosts() {}

    public String getLocation() {return location;}
    public String getHotel() {return hotel;}
    public int getDays() {return days;}
    public int getTotalcost() {return totalcost;}
    public int getTicketprice() {return ticketprice;}
    public int getCostpernight() {return costpernight;}

    public void setLocation(String loc) {location = loc;}
    public void setHotel(String hotel) {this.hotel = hotel;}
    public void setDays(int days) {this.days = days;}
    public void setTotalcost(int cost) {totalcost = cost;}
    public void setTicketprice(int price) {ticketprice = price;}
    public void setCostpernight(int cost) {costpernight = cost;}
}

