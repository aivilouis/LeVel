package umn.ac.id.level;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.Objects;

public class Home extends AppCompatActivity {

    FirebaseDatabase rootNode;
    DatabaseReference ref;

    SharedPreferences sharedPreferences;
    String email;

    RecyclerView erecyclerView;
    ExploreAdapter adapter;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.home_actionbar);
        getSupportActionBar().setElevation(0);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.action_home);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch(item.getItemId()) {
                case R.id.action_home:
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

        sharedPreferences = getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE);
        email = sharedPreferences.getString("EMAIL_KEY", "");

        rootNode = FirebaseDatabase.getInstance("https://level-fecbd-default-rtdb.asia-southeast1.firebasedatabase.app/");
        ref = rootNode.getReference("Posts");

        erecyclerView = findViewById(R.id.homeRecycleView);

        erecyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<ExploreItem> options =
                new FirebaseRecyclerOptions.Builder<ExploreItem>()
                .setQuery(ref, ExploreItem.class)
                .build();

        adapter = new ExploreAdapter(options);
        erecyclerView.setAdapter(adapter);
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
}