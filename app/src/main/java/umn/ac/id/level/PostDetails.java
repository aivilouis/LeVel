package umn.ac.id.level;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Objects;

public class PostDetails extends AppCompatActivity {

    FirebaseDatabase rootNode;
    DatabaseReference refPost, refUser;

    TextView username, location, duration, budget, ticketPrice, hotel;
    ImageView profileImg, locationBtn, thumbnailImg;
    View newView;
    LinearLayout container;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.account_actionbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView title = getSupportActionBar().getCustomView().findViewById(R.id.accountUsername);
        title.setText("More Details");

        String postId = getIntent().getStringExtra("POST");

        rootNode = FirebaseDatabase.getInstance("https://level-fecbd-default-rtdb.asia-southeast1.firebasedatabase.app/");
        refPost = rootNode.getReference("Posts");
        refUser = rootNode.getReference("UserData");

        username = findViewById(R.id.username);
        location = findViewById(R.id.location);
        duration = findViewById(R.id.duration);
        budget = findViewById(R.id.budget);
        profileImg = findViewById(R.id.profileImg);
        locationBtn = findViewById(R.id.locationBtn);
        thumbnailImg = findViewById(R.id.tumbnailImg);
        ticketPrice = findViewById(R.id.costPlane);
        hotel = findViewById(R.id.hotel);
        container = findViewById(R.id.newView);

        ValueEventListener valueEventListener = new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Post post = snapshot.getValue(Post.class);
                assert post != null;
                username.setText(post.getUser());
                username.setOnClickListener(v -> {
                    Intent intent = new Intent(PostDetails.this, UserProfile.class);
                    intent.putExtra("USER", post.getUser());
                    startActivity(intent);
                });

                location.setText(post.getLocation().substring(0,1).toUpperCase() +
                        post.getLocation().substring(1).toLowerCase());
                if (post.getTravelDays() == 1) {
                    duration.setText(post.getTravelDays() + " day");
                } else {
                    duration.setText(post.getTravelDays() + " days");
                }

                budget.setText("Rp " + post.getTotalCost() + ",-");
                hotel.setText(post.getHotel());
                ticketPrice.setText("Rp " + post.getTicketPrice() + ",-");

                byte[] decodedString = Base64.decode(post.getLocationImg(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                thumbnailImg.setImageBitmap(decodedByte);

                ArrayList<Details> postDetails = post.getPostDetails();
                for (int i = 0; i < postDetails.size(); i++) {
                    addView(postDetails.get(i));
                }

                refUser.child(post.getUser()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserData userData = snapshot.getValue(UserData.class);
                        assert userData != null;

                        byte[] decodedString = Base64.decode(userData.getProfPic(), Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        profileImg.setImageBitmap(decodedByte);

                        profileImg.setOnClickListener(v -> {
                            Intent intent = new Intent(PostDetails.this, UserProfile.class);
                            intent.putExtra("USER", userData.getUsername());
                            startActivity(intent);
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        };
        
        refPost.child(postId).addListenerForSingleValueEvent(valueEventListener);
    }

    @SuppressLint("SetTextI18n")
    private void addView(Details details) {
        newView = LayoutInflater.from(this).inflate(R.layout.item_post_details, container, false);

        TextView day = newView.findViewById(R.id.day);
        day.setText(details.getDay());

        TextView hotel = newView.findViewById(R.id.hotelName);
        hotel.setText(details.getDestination());

        ImageView img = newView.findViewById(R.id.destImg);
        byte[] decodedString = Base64.decode(details.getImage(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        img.setImageBitmap(decodedByte);

        RatingBar rating = newView.findViewById(R.id.rating);
        rating.setRating(details.getRating());

        TextView review = newView.findViewById(R.id.review);
        review.setText(details.getReview());

        container.addView(newView, container.getChildCount());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}