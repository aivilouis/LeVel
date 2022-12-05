package umn.ac.id.level;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class AddPostDetails extends AppCompatActivity {

    private EditText etLocation, etDays, etTotalCost, etTicketPrice, etHotel, etCostPerNight;
    SharedPreferences sharedPreferences;
    String email;

    FirebaseDatabase rootNode;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post_details);

        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.addpost_actionbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.cancel_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rootNode = FirebaseDatabase.getInstance("https://level-fecbd-default-rtdb.asia-southeast1.firebasedatabase.app/");

        sharedPreferences = getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE);
        email = sharedPreferences.getString("EMAIL_KEY", "");

        Intent intent = getIntent();
        Bitmap bm = intent.getParcelableExtra("IMG");
        ImageView img = findViewById(R.id.imgView);
        img.setImageBitmap(bm);

        etLocation = findViewById(R.id.input_location);
        etDays = findViewById(R.id.input_days);
        etTotalCost = findViewById(R.id.input_totalcost);
        etTicketPrice = findViewById(R.id.input_ticketprice);
        etHotel = findViewById(R.id.input_hotel);
        etCostPerNight = findViewById(R.id.input_costpernight);
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
            Intent intent = new Intent(AddPostDetails.this, AddPost.class);
            this.startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveData() {
        ref = rootNode.getReference("Posts");

        if (etLocation.length() == 0) etLocation.setError("This field is required");
        else if (etDays.length() == 0) etDays.setError("This field is required");
        else if (etTotalCost.length() == 0) etTotalCost.setError("This field is required");
        else if (etTicketPrice.length() == 0) etTicketPrice.setError("This field is required");
        else if (etHotel.length() == 0) etHotel.setError("This field is required");
        else if (etCostPerNight.length() == 0) etCostPerNight.setError("This field is required");
        else {
            String id = ref.push().getKey();
            String location = etLocation.getText().toString();
            String hotel = etHotel.getText().toString();
            int days = Integer.parseInt(etDays.getText().toString());
            int totalCost = Integer.parseInt(etTotalCost.getText().toString());
            int ticketPrice = Integer.parseInt(etTicketPrice.getText().toString());
            int costPerNight = Integer.parseInt(etCostPerNight.getText().toString());

            Post post = new Post(id, email, location, hotel, days, totalCost, ticketPrice, costPerNight);

            assert id != null;
            ref.child(id).setValue(post);

            Intent intent = new Intent(AddPostDetails.this, AddPostDetails2.class);
            intent.putExtra("POST", post);
            this.startActivity(intent);
        }
    }
}