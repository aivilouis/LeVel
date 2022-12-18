package umn.ac.id.level;

@SuppressWarnings("ALL")
public class Details {
    private String day, destination, review, image;
    private int cost;
    private float rating;

    public Details() {}

    public Details(String day, int cost, String destination, String review, float rating, String image) {
        this.day = day;
        this.cost = cost;
        this.destination = destination;
        this.review = review;
        this.rating = rating;
        this.image = image;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
