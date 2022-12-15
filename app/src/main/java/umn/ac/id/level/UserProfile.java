package umn.ac.id.level;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class UserProfile extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    FirebaseAuth firebaseAuth;
    GoogleSignInClient googleSignInClient;

    FirebaseDatabase rootNode;
    DatabaseReference ref, ref2;

    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    UserAdapter adapter;

    ImageView profileImg;
    TextView country, category, bio;

    @SuppressLint({"NonConstantResourceId", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.account_actionbar);
        getSupportActionBar().setElevation(0);

        TextView username = getSupportActionBar().getCustomView().findViewById(R.id.accountUsername);
        profileImg = findViewById(R.id.accountPicture);
        country = findViewById(R.id.country);
        category = findViewById(R.id.category);
        bio = findViewById(R.id.bio);

        assert user != null;
        String currentUname = user.getDisplayName();
        username.setText(currentUname);

        googleSignInClient= GoogleSignIn.getClient(UserProfile.this, GoogleSignInOptions.DEFAULT_SIGN_IN);

        sharedPreferences = getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE);

        rootNode = FirebaseDatabase.getInstance("https://level-fecbd-default-rtdb.asia-southeast1.firebasedatabase.app/");
        ref = rootNode.getReference("Posts");
        ref2 = rootNode.getReference("UserData");

        ValueEventListener dataListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserData userData = dataSnapshot.getValue(UserData.class);

                assert userData != null;
                byte[] decodedString = Base64.decode(userData.getProfPic(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                profileImg.setImageBitmap(decodedByte);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        };

        assert currentUname != null;
        ref2.child(currentUname).addValueEventListener(dataListener);


        Query query = ref.orderByChild("user").equalTo(user.getDisplayName());

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Post post = snapshot.getValue(Post.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        };

        query.addListenerForSingleValueEvent(valueEventListener);

        mRecyclerView = findViewById(R.id.recyclerview2);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);

        FirebaseRecyclerOptions<ExploreItem> options =
                new FirebaseRecyclerOptions.Builder<ExploreItem>()
                        .setQuery(query, ExploreItem.class)
                        .build();

        adapter = new UserAdapter(options);
        mRecyclerView.setAdapter(adapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.action_home);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch(item.getItemId()) {
                case R.id.action_home:
                    startActivity(new Intent(getApplicationContext(), Home.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.action_addpost:
                    startActivity(new Intent(getApplicationContext(), AddPost.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.action_save:
                    startActivity(new Intent(getApplicationContext(), Saved.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.action_account:
                    startActivity(new Intent(getApplicationContext(), Account.class));
                    overridePendingTransition(0,0);
                    return true;
            }
            return false;
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.close) {
            Intent intent = new Intent(UserProfile.this, Home.class);
            this.startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}