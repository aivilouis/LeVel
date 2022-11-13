package umn.ac.id.level;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class AdapterPosts extends RecyclerView.Adapter<AdapterPosts.ViewHolder> {
    private final LinkedList<ImageButton> feeds;
    private final LayoutInflater mInflater;

    AdapterPosts(Context context, LinkedList<ImageButton> daftarPosts){
        mInflater = LayoutInflater.from(context);
        feeds = daftarPosts;
    }

    @NonNull
    @Override
    public AdapterPosts.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.items, parent, false);
        return new AdapterPosts.ViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPosts.ViewHolder holder, int position) {
        ImageButton mCurrent = feeds.get(position);
        holder.post.setImageResource(Integer.parseInt(String.valueOf(mCurrent)));
    }

    @Override
    public int getItemCount() {
        return feeds.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageButton post;
        final AdapterPosts mAdapter;

        public ViewHolder(@NonNull View itemView, AdapterPosts adapter) {
            super(itemView);
            post = itemView.findViewById(R.id.tableLayout);
            this.mAdapter = adapter;
        }
    }
}