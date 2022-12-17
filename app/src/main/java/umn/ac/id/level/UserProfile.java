package umn.ac.id.level;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class UserProfile extends AppCompatActivity {

    FirebaseDatabase rootNode;
    DatabaseReference refPost, refUserData;

    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    UserAdapter adapter;

    ImageView profileImg, flag, iconCategory;
    TextView country, category, bio;

    String key;

    @SuppressLint({"NonConstantResourceId", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.account_actionbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        key = intent.getStringExtra("USER");

        TextView username = getSupportActionBar().getCustomView().findViewById(R.id.accountUsername);
        profileImg = findViewById(R.id.accountPicture);
        country = findViewById(R.id.country);
        flag = findViewById(R.id.flag);
        category = findViewById(R.id.category);
        iconCategory = findViewById(R.id.iconCategory);
        bio = findViewById(R.id.bio);

        username.setText(key);

        rootNode = FirebaseDatabase.getInstance("https://level-fecbd-default-rtdb.asia-southeast1.firebasedatabase.app/");
        refPost = rootNode.getReference("Posts");
        refUserData = rootNode.getReference("UserData");

        ValueEventListener dataListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserData userData = dataSnapshot.getValue(UserData.class);

                assert userData != null;
                byte[] decodedString = Base64.decode(userData.getProfPic(), Base64.DEFAULT);
                Glide.with(getApplicationContext())
                        .asBitmap()
                        .load(decodedString)
                        .apply(new RequestOptions()
                            .skipMemoryCache(true)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .dontTransform())
                        .into(profileImg);

                country.setText(userData.getCountry());
                category.setText(userData.getCategory());
                bio.setText(userData.getBio());

                String negara = userData.getCountry().toLowerCase();
                negara = negara.replaceAll("\\s","_");
                int id = getResources().getIdentifier("umn.ac.id.level:drawable/flag_" + negara, null, null);
                flag.setImageResource(id);

                String kategori = userData.getCategory().toLowerCase();
                kategori = kategori.replaceAll("\\s","_");
                int c = getResources().getIdentifier("umn.ac.id.level:drawable/" + kategori, null, null);
                iconCategory.setImageResource(c);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        };

        refUserData.child(key).addValueEventListener(dataListener);


        Query query = refPost.orderByChild("user").equalTo(key);

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Post post = snapshot.getValue(Post.class);

                if(post == null){
                    findViewById(R.id.koper).setVisibility(View.INVISIBLE);
                    findViewById(R.id.nopost).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        };

        query.addListenerForSingleValueEvent(valueEventListener);

        mRecyclerView = findViewById(R.id.recyclerview2);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setItemAnimator(null);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);

        FirebaseRecyclerOptions<ExploreItem> options =
                new FirebaseRecyclerOptions.Builder<ExploreItem>()
                        .setQuery(query, ExploreItem.class)
                        .build();

        adapter = new UserAdapter(options, getApplicationContext());
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
                case R.id.action_location:
                    startActivity(new Intent(getApplicationContext(), MapsActivity.class));
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