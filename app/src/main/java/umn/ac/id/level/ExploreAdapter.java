package umn.ac.id.level;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ExploreAdapter extends RecyclerView.Adapter<ExploreViewHolder> {
    Context context;
    List<ExploreItem> items;

    public ExploreAdapter(Context context, List<ExploreItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ExploreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ExploreViewHolder(LayoutInflater.from(context).inflate(R.layout.home_recycler_view_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ExploreViewHolder holder, int position) {
    holder.profileView.setImageResource(items.get(position).getProfileImage());
    holder.locationView.setImageResource(items.get(position).getLocationImage());
    holder.iconSavedView.setImageResource(items.get(position).getIconSaved());
    holder.iconDurationView.setImageResource(items.get(position).getIconDuration());
    holder.iconBudgetView.setImageResource(items.get(position).getIconBudget());
    holder.profileTxtView.setText(items.get(position).getNamaAkun());
    holder.locationTxtView.setText(items.get(position).getNamaLokasi());
    holder.durationTxtView.setText(items.get(position).getDurasiTrip());
    holder.budgetTxtView.setText(items.get(position).getTotalBudget());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
