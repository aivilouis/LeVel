package umn.ac.id.level;


import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SavedAdapter extends RecyclerView.Adapter<SavedViewHolder> {
    Context context;
    List<SavedItem> itemss;

    public SavedAdapter(Context context, List<SavedItem> itemss) {
        this.context = context;
        this.itemss = itemss;
    }

    @NonNull
    @Override
    public SavedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SavedViewHolder(LayoutInflater.from(context).inflate(R.layout.saved_recycler_view_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SavedViewHolder holder, int position) {
        holder.profileViewSaved.setImageResource(itemss.get(position).getProfileImagesaved());
        holder.locationViewSaved.setImageResource(itemss.get(position).getLocationImagesaved());
        holder.iconSavedViewSaved.setImageResource(itemss.get(position).getIconSavedsaved());
        holder.iconShareViewSaved.setImageResource(itemss.get(position).getIconSharesaved());
        holder.iconDurationViewSaved.setImageResource(itemss.get(position).getIconDurationsaved());
        holder.iconBudgetViewSaved.setImageResource(itemss.get(position).getIconBudgetsaved());
        holder.profileTxtViewSaved.setText(itemss.get(position).getNamaAkunsaved());
        holder.locationTxtViewSaved.setText(itemss.get(position).getNamaLokasisaved());
        holder.durationTxtViewSaved.setText(itemss.get(position).getDurasiTripsaved());
        holder.budgetTxtViewSaved.setText(itemss.get(position).getTotalBudgetsaved());

    }

    @Override
    public int getItemCount() {
        return itemss.size();
    }
}

