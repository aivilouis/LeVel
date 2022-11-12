package umn.ac.id.level;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class InputAdapter extends RecyclerView.Adapter<InputAdapter.ViewHolder> {

    private final LinkedList<String> mDays;
    private final LayoutInflater mInflater;

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
        holder.tv.setText(mCurrent);
    }

    @Override
    public int getItemCount() {
        return mDays.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView tv;
        final InputAdapter mAdapter;

        public ViewHolder(@NonNull View itemView, InputAdapter adapter) {
            super(itemView);
            tv = itemView.findViewById(R.id.title);
            this.mAdapter = adapter;
        }
    }
}

