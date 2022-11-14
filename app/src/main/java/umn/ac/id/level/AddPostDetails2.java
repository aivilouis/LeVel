package umn.ac.id.level;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.LinkedList;
import java.util.Objects;

public class AddPostDetails2 extends AppCompatActivity {

    LinkedList<String> totalDays = new LinkedList<>();
    EditText destination, cost, review;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post_details2);

        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.addpost_actionbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.cancel_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        int days = Integer.parseInt(intent.getStringExtra("DAYS"));

        for (int i = 1; i <= days; i++) {
            totalDays.add("Day " + i);
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        InputAdapter mAdapter = new InputAdapter(this, totalDays);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        destination = mRecyclerView.findViewById(R.id.input_destination);
        cost = mRecyclerView.findViewById(R.id.input_cost);
        review = mRecyclerView.findViewById(R.id.input_review);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_addpostdetails, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_done) {
            if (InputAdapter.valid) {
                Intent intent = new Intent(AddPostDetails2.this, Home.class);
                this.startActivity(intent);
                return true;
            }
        }
        if (id == android.R.id.home) {
            Intent intent = new Intent(AddPostDetails2.this, AddPostDetails.class);
            this.startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}