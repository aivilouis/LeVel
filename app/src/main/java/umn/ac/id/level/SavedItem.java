package umn.ac.id.level;

import android.widget.Button;

public class SavedItem {
    String namaAkunsaved;
    String namaLokasisaved;
    String durasiTripsaved;
    String totalBudgetsaved;
    int detailsButtonsaved;
    int profileImagesaved;
    int locationImagesaved;
    int iconSavedsaved;
    int iconSharesaved;
    int iconDurationsaved;
    int iconBudgetsaved;

    public SavedItem(String namaAkunsaved, String namaLokasisaved, String durasiTripsaved, String totalBudgetsaved, int detailsButtonsaved, int profileImagesaved, int locationImagesaved, int iconSavedsaved, int iconSharesaved, int iconDurationsaved, int iconBudgetsaved) {
        this.namaAkunsaved = namaAkunsaved;
        this.namaLokasisaved = namaLokasisaved;
        this.durasiTripsaved = durasiTripsaved;
        this.totalBudgetsaved = totalBudgetsaved;
        this.detailsButtonsaved = detailsButtonsaved;
        this.profileImagesaved = profileImagesaved;
        this.locationImagesaved = locationImagesaved;
        this.iconSavedsaved = iconSavedsaved;
        this.iconSharesaved = iconSharesaved;
        this.iconDurationsaved = iconDurationsaved;
        this.iconBudgetsaved = iconBudgetsaved;
    }


    public String getNamaAkunsaved() {
        return namaAkunsaved;
    }

    public String getNamaLokasisaved() {return namaLokasisaved;}

    public String getDurasiTripsaved() {
        return durasiTripsaved;
    }

    public String getTotalBudgetsaved() {
        return totalBudgetsaved;
    }

    public int getDetailsButtonsaved() {
        return detailsButtonsaved;
    }

    public int getProfileImagesaved() {
        return profileImagesaved;
    }

    public int getLocationImagesaved() {
        return locationImagesaved;
    }

    public int getIconSavedsaved() {
        return iconSavedsaved;
    }

    public int getIconSharesaved() {
        return iconSharesaved;
    }

    public int getIconDurationsaved() {
        return iconDurationsaved;
    }

    public int getIconBudgetsaved() {
        return iconBudgetsaved;
    }

}

