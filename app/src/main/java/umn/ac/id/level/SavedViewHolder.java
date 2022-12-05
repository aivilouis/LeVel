package umn.ac.id.level;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SavedViewHolder extends RecyclerView.ViewHolder {
    ImageView profileViewSaved, locationViewSaved, iconSavedViewSaved, iconShareViewSaved,iconDurationViewSaved, iconBudgetViewSaved;
    TextView profileTxtViewSaved, locationTxtViewSaved, durationTxtViewSaved, budgetTxtViewSaved;
    Button detailsButtonViewSaved;

    public SavedViewHolder(@NonNull View itemView) {
        super(itemView);
        profileViewSaved = itemView.findViewById(R.id.profileImgSaved);
        locationViewSaved = itemView.findViewById(R.id.locationImgSaved);
        iconSavedViewSaved = itemView.findViewById(R.id.iconSavedSaved);
        iconShareViewSaved= itemView.findViewById(R.id.iconShareSaved);
        iconBudgetViewSaved = itemView.findViewById(R.id.iconBudgetSaved);
        iconDurationViewSaved = itemView.findViewById(R.id.iconDurationSaved);
        detailsButtonViewSaved = itemView.findViewById(R.id.detailsBtn);
        profileTxtViewSaved = itemView.findViewById(R.id.profileTxtSaved);
        locationTxtViewSaved = itemView.findViewById(R.id.locationTxtSaved);
        durationTxtViewSaved = itemView.findViewById(R.id.durationTxtSaved);
        budgetTxtViewSaved = itemView.findViewById(R.id.budgetTxtSaved);
    }
}
