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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ExploreAdapter extends
        FirebaseRecyclerAdapter<ExploreItem, ExploreAdapter.ExploreViewHolder> {
    private final ExploreRecyclerInterface exploreRecyclerInterface;
    FirebaseDatabase rootNode = FirebaseDatabase.getInstance("https://level-fecbd-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference ref = rootNode.getReference("UserData");

    public ExploreAdapter(@NonNull FirebaseRecyclerOptions<ExploreItem> options,ExploreRecyclerInterface exploreRecyclerInterface) {
        super(Objects.requireNonNull(options));
        this.exploreRecyclerInterface = exploreRecyclerInterface;
    }

    @NonNull
    @Override
    public ExploreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_recycler_view_row, parent, false);
        return new ExploreViewHolder(view,exploreRecyclerInterface);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull ExploreAdapter.ExploreViewHolder holder, int position, @NonNull ExploreItem model) {
        holder.user.setText(model.getUser());
        holder.location.setText(model.getLocation());

        if (model.getTravelDays() == 1) {
            holder.travelDays.setText(model.getTravelDays() + " day");
        } else {
            holder.travelDays.setText(model.getTravelDays() + " days");
        }

        holder.totalCost.setText("Rp " + model.getTotalCost() + ",-");

        byte[] decodedString = Base64.decode(model.getLocationImg(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.locationImg.setImageBitmap(decodedByte);

        ValueEventListener dataListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserData userData = dataSnapshot.getValue(UserData.class);
                assert userData != null;
                byte[] decodedString2 = Base64.decode(userData.getProfPic(), Base64.DEFAULT);
                Bitmap decodedByte2 = BitmapFactory.decodeByteArray(decodedString2, 0, decodedString2.length);
                holder.profileImg.setImageBitmap(decodedByte2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        };

        ref.child(model.getUser()).addValueEventListener(dataListener);

        String username = holder.user.getText().toString();

        holder.profileImg.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), UserProfile.class);
            intent.putExtra("USER", username);
            v.getContext().startActivity(intent);
        });

        holder.user.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), UserProfile.class);
            intent.putExtra("USER", username);
            v.getContext().startActivity(intent);
        });

        holder.iconLocation.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), MapsActivity.class);
            intent.putExtra("LOCATION", model.getLocation());
            v.getContext().startActivity(intent);
        });
    }

    static class ExploreViewHolder extends RecyclerView.ViewHolder {
        TextView user, location, travelDays, totalCost;
        ImageView profileImg, locationImg, iconLocation;

        public ExploreViewHolder(@NonNull View itemView,ExploreRecyclerInterface exploreRecyclerInterface) {
            super(itemView);
            user = itemView.findViewById(R.id.user);
            location = itemView.findViewById(R.id.location);
            travelDays = itemView.findViewById(R.id.travelDays);
            totalCost = itemView.findViewById(R.id.totalCost);
            profileImg = itemView.findViewById(R.id.profileImg);
            locationImg = itemView.findViewById(R.id.locationImg);
            iconLocation = itemView.findViewById(R.id.iconLocation);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if(exploreRecyclerInterface != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            exploreRecyclerInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
