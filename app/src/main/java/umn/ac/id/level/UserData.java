package umn.ac.id.level;

public class UserData {
    String username, email, profileImg;

    public UserData() {}

    public UserData(String username, String email, String profileImg) {
        this.username = username;
        this.email = email;
        this.profileImg = profileImg;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
