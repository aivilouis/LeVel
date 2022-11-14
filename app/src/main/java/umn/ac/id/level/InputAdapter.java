package umn.ac.id.level;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class InputAdapter extends RecyclerView.Adapter<InputAdapter.ViewHolder> {

    LinkedList<String> mDays;
    LayoutInflater mInflater;
    boolean onChange = false;
    public static boolean valid = false;

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
        holder.destination.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                onChange = true;
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (onChange) {
                    onChange = false;
                    for (int i = 0; i <= holder.getAdapterPosition(); i++) {
                        if (i == holder.getAdapterPosition()) {
                            if (editable.toString().length() == 0) {
                                holder.destination.setError("This field is required");
                                valid = false;
                            } else {
                                valid = true;
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDays.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        EditText destination;
        InputAdapter mAdapter;

        public ViewHolder(@NonNull View itemView, InputAdapter adapter) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            destination = itemView.findViewById(R.id.input_destination);
            this.mAdapter = adapter;
        }
    }
}

