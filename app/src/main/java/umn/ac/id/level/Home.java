package umn.ac.id.level;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Home extends AppCompatActivity {

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

        //Home(Explore)
        RecyclerView erecyclerView = findViewById(R.id.homeRecycleView);

        //Listitem
        List<ExploreItem>  items = new ArrayList<ExploreItem>();
        items.add(new ExploreItem("han.sohee","BALI, INDONESIA","2 days (20 October 2022)","Rp 5.000.000,-",R.drawable.picture,R.drawable.bali,R.drawable.icon_saved,R.drawable.icon_share,R.drawable.icon_days,R.drawable.icon_totalcost));
        items.add(new ExploreItem("taoPhiang77","BANGKOK, THAILAND","5 days (7 November 2022)","Rp 6.000.000,-",R.drawable.profile1,R.drawable.explore_thailand,R.drawable.icon_saved_selected,R.drawable.icon_share,R.drawable.icon_days,R.drawable.icon_totalcost));
        items.add(new ExploreItem("Nichk_kun","HANOI, VIETNAM","3 days (15 August 2022)","Rp 3.500.000,-",R.drawable.profile2,R.drawable.explore_vietnam,R.drawable.icon_saved,R.drawable.icon_share,R.drawable.icon_days,R.drawable.icon_totalcost));
        items.add(new ExploreItem("won.young211","SARAWAK, MALAYSIA","2 days (10 July 2022)","Rp 5.000.000,-",R.drawable.profile3,R.drawable.explore_malaysia,R.drawable.icon_saved_selected,R.drawable.icon_share,R.drawable.icon_days,R.drawable.icon_totalcost));

        //Explore recycle
        erecyclerView.setLayoutManager(new LinearLayoutManager(this));
        erecyclerView.setAdapter(new ExploreAdapter(getApplicationContext(),items));


    }

}