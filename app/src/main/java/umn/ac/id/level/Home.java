package umn.ac.id.level;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.Objects;

public class Home extends AppCompatActivity {

    FirebaseDatabase rootNode;
    DatabaseReference ref;

    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
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
                    startActivity(new Intent(getApplicationContext(), Location.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.action_account:
                    startActivity(new Intent(getApplicationContext(), Account.class));
                    overridePendingTransition(0,0);
                    return true;
            }
            return false;
        });

        rootNode = FirebaseDatabase.getInstance("https://level-fecbd-default-rtdb.asia-southeast1.firebasedatabase.app/");
        ref = rootNode.getReference("Posts");

        recyclerView = findViewById(R.id.homeRecyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(mLayoutManager);

        FirebaseRecyclerOptions<ExploreItem> options =
                new FirebaseRecyclerOptions.Builder<ExploreItem>()
                .setQuery(ref, ExploreItem.class)
                .build();

        adapter = new ExploreAdapter(options);
        recyclerView.setAdapter(adapter);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onStart() {
        super.onStart();
        recyclerView.getRecycledViewPool().clear();
        adapter.startListening();

        recyclerView.getRecycledViewPool().clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}