package umn.ac.id.level;

public class UserData {
    String id, username, email, profileImg;

    public UserData() {}

    public UserData(String id, String username, String email, String profileImg) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.profileImg = profileImg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
