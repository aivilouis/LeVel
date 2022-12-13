package umn.ac.id.level;

public class UserData {
    String username, country, category, bio;
    int categoryId;

    public UserData() {}

    public UserData(String username, String country, int categoryId, String category, String bio) {
        this.username = username;
        this.country = country;
        this.categoryId = categoryId;
        this.category = category;
        this.bio = bio;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
