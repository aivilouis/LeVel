package umn.ac.id.level;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PostDetails extends AppCompatActivity {

    FirebaseDatabase rootNode;
    DatabaseReference refPost, refUser;

    TextView username, location, duration, budget;
    ImageView profileImg, locationBtn, thumbnailImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

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

        ValueEventListener valueEventListener = new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Post post = snapshot.getValue(Post.class);
                assert post != null;
                username.setText(post.getUser());
                location.setText(post.getLocation());
                duration.setText(post.getTravelDays() + " days");
                budget.setText("Rp " + post.getTotalCost() + ",-");

                byte[] decodedString = Base64.decode(post.getLocationImg(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                thumbnailImg.setImageBitmap(decodedByte);

                refUser.child(post.getUser()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserData userData = snapshot.getValue(UserData.class);
                        assert userData != null;

                        byte[] decodedString = Base64.decode(userData.getProfPic(), Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        profileImg.setImageBitmap(decodedByte);
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
}