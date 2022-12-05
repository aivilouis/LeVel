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
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

public class AddPostDetails2 extends AppCompatActivity {

    LinkedList<String> totalDays = new LinkedList<>();
    EditText etDestination, etCost, etReview;
    RecyclerView mRecyclerView;

    FirebaseDatabase rootNode;
    DatabaseReference ref, postRef;
    Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post_details2);

        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.addpost_actionbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.cancel_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rootNode = FirebaseDatabase.getInstance("https://level-fecbd-default-rtdb.asia-southeast1.firebasedatabase.app/");

        Intent intent = getIntent();
        post = (Post)intent.getSerializableExtra("POST");

        for (int i = 1; i <= post.getTravelDays(); i++) {
            totalDays.add("Day " + i);
        }

        mRecyclerView = findViewById(R.id.recyclerview);
        InputAdapter mAdapter = new InputAdapter(this, totalDays);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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
            saveData();
            return true;
        }
        if (id == android.R.id.home) {
            Intent intent = new Intent(AddPostDetails2.this, AddPostDetails.class);
            this.startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveData() {
        ref = rootNode.getReference("Posts");
        postRef = ref.child(post.getId()).child("postDetails");
//        Map<String, Object> postDetails = new HashMap<>();

        for (int i = 0; i < mRecyclerView.getChildCount(); ++i) {
            RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(mRecyclerView.getChildAt(i));

            etDestination = holder.itemView.findViewById(R.id.input_destination);
            etCost = holder.itemView.findViewById(R.id.input_cost);
            etReview = holder.itemView.findViewById(R.id.input_review);

            if (etDestination.length() == 0 || etCost.length() == 0 || etReview.length() == 0) {
                Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_LONG).show();
            }
            else {
                String destination = etDestination.getText().toString();
                int cost = Integer.parseInt(etCost.getText().toString());
                String review = etReview.getText().toString();
                String detailsID = postRef.push().getKey();

//                postDetails.put(detailsID, new Details(i+1, cost, destination, review, 0.0f));
                assert detailsID != null;
                postRef.child(detailsID).setValue(new Details(i+1, cost, destination, review, 0.0f));
            }
        }

//        postRef.setValue(postDetails);

        Intent intent = new Intent(AddPostDetails2.this, Home.class);
        this.startActivity(intent);
    }
}