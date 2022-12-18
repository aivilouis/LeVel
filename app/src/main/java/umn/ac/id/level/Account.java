package umn.ac.id.level;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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

public class Account extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    FirebaseAuth firebaseAuth;

    FirebaseDatabase rootNode;
    DatabaseReference ref, ref2;

    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    AccountAdapter adapter;

    ImageView profileImg, flag, iconCategory;
    TextView country, category, bio;


    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.account_actionbar);
        getSupportActionBar().setElevation(0);

        TextView username = getSupportActionBar().getCustomView().findViewById(R.id.accountUsername);
        profileImg = findViewById(R.id.accountPicture);
        country = findViewById(R.id.country);
        flag = findViewById(R.id.flag);
        category = findViewById(R.id.category);
        iconCategory = findViewById(R.id.iconCategory);
        bio = findViewById(R.id.bio);

        assert user != null;
        String currentUname = user.getDisplayName();
        username.setText(currentUname);

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

        assert currentUname != null;
        ref2.child(currentUname).addValueEventListener(dataListener);


        Query query = ref.orderByChild("user").equalTo(user.getDisplayName());

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

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);

        FirebaseRecyclerOptions<ExploreItem> options =
                new FirebaseRecyclerOptions.Builder<ExploreItem>()
                        .setQuery(query, ExploreItem.class)
                        .build();

        adapter = new AccountAdapter(options, getApplicationContext());
        mRecyclerView.setAdapter(adapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.action_account);

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
                    return true;
            }
            return false;
        });

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onStart() {
        super.onStart();
        mRecyclerView.getRecycledViewPool().clear();
        adapter.startListening();

        mRecyclerView.getRecycledViewPool().clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_account, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                Intent intentEdit = new Intent(Account.this, EditAccount.class);
                startActivity(intentEdit);
                return true;
            case R.id.faq:
                Intent intentFAQ = new Intent(Account.this, FAQ.class);
                startActivity(intentFAQ);
                return true;
            case R.id.signout:
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                FirebaseAuth.getInstance().signOut();

                Intent i = new Intent(Account.this, Login.class);
                startActivity(i);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}