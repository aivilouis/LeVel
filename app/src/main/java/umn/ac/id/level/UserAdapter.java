package umn.ac.id.level;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.Objects;

public class UserAdapter extends
        FirebaseRecyclerAdapter<ExploreItem, UserAdapter.UserViewHolder> {

    public UserAdapter(@NonNull FirebaseRecyclerOptions<ExploreItem> options) {
        super(Objects.requireNonNull(options));
    }

    @NonNull
    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.account_recycler, parent, false);
        return new UserAdapter.UserViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull UserAdapter.UserViewHolder holder, int position, @NonNull ExploreItem model) {
        byte[] decodedString = Base64.decode(model.getLocationImg(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.post.setImageBitmap(decodedByte);
        holder.location.setText(model.getLocation());
        holder.post.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), PostDetails.class);
            intent.putExtra("POST", model.getId());
            v.getContext().startActivity(intent);
        });
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView location;
        ImageView post;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            post = itemView.findViewById(R.id.post);
            location = itemView.findViewById(R.id.location);
        }
    }
}