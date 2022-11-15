package umn.ac.id.level;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ExploreViewHolder extends RecyclerView.ViewHolder {
    ImageView profileView, locationView, iconSavedView, iconShareView,iconDurationView, iconBudgetView;
    TextView profileTxtView, locationTxtView, durationTxtView, budgetTxtView;

    public ExploreViewHolder(@NonNull View itemView) {
        super(itemView);
        profileView = itemView.findViewById(R.id.profileImg);
        locationView = itemView.findViewById(R.id.locationImg);
        iconSavedView = itemView.findViewById(R.id.iconSaved);
        iconShareView = itemView.findViewById(R.id.iconShare);
        iconBudgetView = itemView.findViewById(R.id.iconBudget);
        iconDurationView = itemView.findViewById(R.id.iconDuration);
        profileTxtView = itemView.findViewById(R.id.profileTxt);
        locationTxtView = itemView.findViewById(R.id.locationTxt);
        durationTxtView = itemView.findViewById(R.id.durationTxt);
        budgetTxtView = itemView.findViewById(R.id.budgetTxt);
    }
}
