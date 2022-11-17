package umn.ac.id.level;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountViewHolder> {
    Context context;
    List<Posts> items;

    public AccountAdapter(Context context, List<Posts> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AccountViewHolder(LayoutInflater.from(context).inflate(R.layout.account_recycler,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        holder.post1.setImageResource(items.get(position).getPost1());
        holder.post2.setImageResource(items.get(position).getPost2());
        holder.post3.setImageResource(items.get(position).getPost3());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}