package umn.ac.id.level;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class InputAdapter extends RecyclerView.Adapter<InputAdapter.ViewHolder> {

    LinkedList<String> mDays;
    LayoutInflater mInflater;

    InputAdapter(Context context, LinkedList<String> days) {
        mInflater = LayoutInflater.from(context);
        mDays = days;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.items, parent, false);
        return new ViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String mCurrent = mDays.get(position);
        holder.title.setText(mCurrent);

        holder.addPhoto.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent();
            intent.setAction(android.content.Intent.ACTION_VIEW);
            intent.setType("image/*");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mDays.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        Button addPhoto;
        InputAdapter mAdapter;

        public ViewHolder(@NonNull View itemView, InputAdapter adapter) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            addPhoto = itemView.findViewById(R.id.addPhotoBtn);
            this.mAdapter = adapter;
        }
    }
}

