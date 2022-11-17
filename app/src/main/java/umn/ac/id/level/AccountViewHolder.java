package umn.ac.id.level;

import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AccountViewHolder extends RecyclerView.ViewHolder {
    ImageButton post1, post2, post3;

    public AccountViewHolder(@NonNull View itemView) {
        super(itemView);
        post1 = itemView.findViewById(R.id.post1);
        post2 = itemView.findViewById(R.id.post2);
        post3 = itemView.findViewById(R.id.post3);
    }
}
