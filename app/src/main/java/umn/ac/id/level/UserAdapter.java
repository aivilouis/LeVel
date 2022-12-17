package umn.ac.id.level;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.Objects;

public class UserAdapter extends
        FirebaseRecyclerAdapter<ExploreItem, UserAdapter.UserViewHolder> {

    Context context;

    public UserAdapter(@NonNull FirebaseRecyclerOptions<ExploreItem> options, Context context) {
        super(Objects.requireNonNull(options));
        this.context = context;
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
        Glide.with(this.context)
                .asBitmap()
                .load(decodedString)
                .apply(new RequestOptions()
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontTransform())
                .into(holder.post);

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