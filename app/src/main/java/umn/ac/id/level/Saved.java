package umn.ac.id.level;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Saved extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);

        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.saved_actionbar);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.action_save);

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
                    return true;
                case R.id.action_account:
                    startActivity(new Intent(getApplicationContext(), Account.class));
                    overridePendingTransition(0,0);
                    return true;
            }
            return false;
        });
        //Saved
        RecyclerView srecyclerView = findViewById(R.id.savedRecycleView);

        //Listitem
        List<SavedItem> itemss = new ArrayList<SavedItem>();
        itemss.add(new SavedItem("taoPhiang77","BANGKOK, THAILAND","5 days (7 November 2022)","Rp 6.000.000,-",R.drawable.profile1,R.drawable.explore_thailand,R.drawable.icon_saved_selected,R.drawable.icon_share,R.drawable.icon_days,R.drawable.icon_totalcost));
        itemss.add(new SavedItem("won.young211","SARAWAK, MALAYSIA","2 days (10 July 2022)","Rp 5.000.000,-",R.drawable.profile3,R.drawable.explore_malaysia,R.drawable.icon_saved_selected,R.drawable.icon_share,R.drawable.icon_days,R.drawable.icon_totalcost));

        //Explore recycle
        srecyclerView.setLayoutManager(new LinearLayoutManager(this));
        srecyclerView.setAdapter(new SavedAdapter(getApplicationContext(),itemss));
    }
}