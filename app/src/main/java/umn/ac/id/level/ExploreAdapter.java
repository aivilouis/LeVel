package umn.ac.id.level;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class ExploreAdapter extends
        FirebaseRecyclerAdapter<ExploreItem, ExploreAdapter.ExploreViewHolder> {

    public ExploreAdapter(@NonNull FirebaseRecyclerOptions<ExploreItem> options) {
        super(Objects.requireNonNull(options));
    }

    @NonNull
    @Override
    public ExploreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_recycler_view_row, parent, false);
        return new ExploreViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull ExploreAdapter.ExploreViewHolder holder, int position, @NonNull ExploreItem model) {
        holder.user.setText(model.getUser());
        holder.location.setText(model.getLocation());
        holder.travelDays.setText(model.getTravelDays() + " days");
        holder.totalCost.setText("Rp " + model.getTotalCost() + ",-");
        new ImageLoadTask("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png",
                holder.profileImg).execute();
    }

    static class ExploreViewHolder extends RecyclerView.ViewHolder {
        TextView user, location, travelDays, totalCost;
        ImageView profileImg;

        public ExploreViewHolder(@NonNull View itemView) {
            super(itemView);
            user = itemView.findViewById(R.id.user);
            location = itemView.findViewById(R.id.location);
            travelDays = itemView.findViewById(R.id.travelDays);
            totalCost = itemView.findViewById(R.id.totalCost);
            profileImg = itemView.findViewById(R.id.profileImg);
        }
    }
}
