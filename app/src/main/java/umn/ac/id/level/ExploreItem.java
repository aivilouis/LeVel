package umn.ac.id.level;

public class ExploreItem {
    String namaAkun;
    String namaLokasi;
    String durasiTrip;
    String totalBudget;
    int profileImage;
    int locationImage;
    int iconSaved;
    int iconDuration;
    int iconBudget;

    public ExploreItem(String namaAkun, String namaLokasi, String durasiTrip, String totalBudget, int profileImage, int locationImage, int iconSaved, int iconDuration, int iconBudget) {
        this.namaAkun = namaAkun;
        this.namaLokasi = namaLokasi;
        this.durasiTrip = durasiTrip;
        this.totalBudget = totalBudget;
        this.profileImage = profileImage;
        this.locationImage = locationImage;
        this.iconSaved = iconSaved;
        this.iconDuration = iconDuration;
        this.iconBudget = iconBudget;
    }

    public String getNamaAkun() {
        return namaAkun;
    }

    public String getNamaLokasi() {
        return namaLokasi;
    }

    public String getDurasiTrip() {
        return durasiTrip;
    }

    public String getTotalBudget() {
        return totalBudget;
    }

    public int getProfileImage() {
        return profileImage;
    }

    public int getLocationImage() {
        return locationImage;
    }

    public int getIconSaved() {
        return iconSaved;
    }

    public int getIconDuration() {
        return iconDuration;
    }

    public int getIconBudget() {
        return iconBudget;
    }

    public void setNamaAkun(String namaAkun) {
        this.namaAkun = namaAkun;
    }

    public void setNamaLokasi(String namaLokasi) {
        this.namaLokasi = namaLokasi;
    }

    public void setDurasiTrip(String durasiTrip) {
        this.durasiTrip = durasiTrip;
    }

    public void setTotalBudget(String totalBudget) {
        this.totalBudget = totalBudget;
    }

    public void setProfileImage(int profileImage) {
        this.profileImage = profileImage;
    }

    public void setLocationImage(int locationImage) {
        this.locationImage = locationImage;
    }

    public void setIconSaved(int iconSaved) {
        this.iconSaved = iconSaved;
    }

    public void setIconDuration(int iconDuration) {
        this.iconDuration = iconDuration;
    }

    public void setIconBudget(int iconBudget) {
        this.iconBudget = iconBudget;
    }
}
