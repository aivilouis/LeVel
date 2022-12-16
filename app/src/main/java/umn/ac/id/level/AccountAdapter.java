package umn.ac.id.level;

import android.annotation.SuppressLint;
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

public class AccountAdapter extends
        FirebaseRecyclerAdapter<ExploreItem, AccountAdapter.AccountViewHolder> {

    public AccountAdapter(@NonNull FirebaseRecyclerOptions<ExploreItem> options) {
        super(Objects.requireNonNull(options));
    }

    @NonNull
    @Override
    public AccountAdapter.AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.account_recycler, parent, false);
        return new AccountAdapter.AccountViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull AccountAdapter.AccountViewHolder holder, int position, @NonNull ExploreItem model) {
        byte[] decodedString = Base64.decode(model.getLocationImg(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.post.setImageBitmap(decodedByte);
        holder.location.setText(model.getLocation());
    }

    static class AccountViewHolder extends RecyclerView.ViewHolder {
        TextView location;
        ImageView post;

        public AccountViewHolder(@NonNull View itemView) {
            super(itemView);
            post = itemView.findViewById(R.id.post);
            location = itemView.findViewById(R.id.location);
        }
    }
}