package umn.ac.id.level;

public class UserData {
    String profPic, username, countryCode, country, category, bio;
    int categoryId;

    public UserData() {}

    public UserData(String profPic, String username, String countryCode,
                    String country, int categoryId, String category, String bio) {
        this.profPic = profPic;
        this.username = username;
        this.countryCode = countryCode;
        this.country = country;
        this.categoryId = categoryId;
        this.category = category;
        this.bio = bio;
    }

    public String getProfPic() {
        return profPic;
    }

    public void setProfPic(String profPic) {
        this.profPic = profPic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
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
