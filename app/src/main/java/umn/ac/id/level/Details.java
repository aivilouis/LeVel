package umn.ac.id.level;

public class Details {
    private int day, cost;
    private String destination, review;
    private float rating;

    public Details() {}

    public Details(int day, int cost, String destination, String review, float rating) {
        this.day = day;
        this.cost = cost;
        this.destination = destination;
        this.review = review;
        this.rating = rating;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
