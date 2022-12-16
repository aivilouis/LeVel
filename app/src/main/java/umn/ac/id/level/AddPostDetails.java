package umn.ac.id.level;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Switch;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class AddPostDetails extends AppCompatActivity {

    EditText etLocation, etDays, etTotalCost, etTicketPrice, etHotel, etCostPerNight;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch roundTrip;
    LinearLayout container;
    Bitmap bitmap;
    Uri uri, locationUri;
    View newView;
    ArrayList<Details> postDetails = new ArrayList<>();

    FirebaseDatabase rootNode;
    DatabaseReference ref;

    ActivityResultLauncher<Intent> activityResultLauncher;
    ImageView locationImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post_details);

        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.addpost_actionbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.cancel_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rootNode = FirebaseDatabase.getInstance("https://level-fecbd-default-rtdb.asia-southeast1.firebasedatabase.app/");

        Intent intent = getIntent();
        ImageView img = findViewById(R.id.imgView);

        if (intent.getStringExtra("SOURCE").contentEquals("camera")){
            bitmap = intent.getParcelableExtra("IMG");
            img.setImageBitmap(bitmap);
        } else if (intent.getStringExtra("SOURCE").contentEquals("gallery")){
            uri = intent.getParcelableExtra("IMG");
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            img.setImageURI(uri);
        }

        etLocation = findViewById(R.id.input_location);
        etDays = findViewById(R.id.input_days);
        etTotalCost = findViewById(R.id.input_totalcost);
        etTicketPrice = findViewById(R.id.input_ticketprice);
        etHotel = findViewById(R.id.input_hotel);
        etCostPerNight = findViewById(R.id.input_costpernight);
        roundTrip = findViewById(R.id.roundTrip);


        container = findViewById(R.id.newView);
        Button addDestination = findViewById(R.id.addDestinationBtn);
        addDestination.setOnClickListener(v -> addNewView());

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        assert data != null;
                        locationUri = data.getData();
                        locationImg.setVisibility(View.VISIBLE);
                        locationImg.setImageURI(locationUri);
                    }
                }
        );
    }

    private void addNewView() {
        if (etLocation.length() == 0) {
            etLocation.setError("This field is required");
            return;
        }
        if (etDays.length() == 0) {
            etDays.setError("This field is required");
            return;
        }
        if (etTotalCost.length() == 0) {
            etTotalCost.setError("This field is required");
            return;
        }
        if (etTicketPrice.length() == 0) {
            etTicketPrice.setError("This field is required");
            return;
        }
        if (etHotel.length() == 0) {
            etHotel.setError("This field is required");
            return;
        }
        if (etCostPerNight.length() == 0) {
            etCostPerNight.setError("This field is required");
            return;
        }

        int totalDays = Integer.parseInt(etDays.getText().toString());

        newView = LayoutInflater.from(this).inflate(R.layout.items, container, false);
        locationImg = newView.findViewById(R.id.locationImg);

        newView.findViewById(R.id.addPhotoBtn).setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            activityResultLauncher.launch(intent);
        });

        Spinner dropdown = newView.findViewById(R.id.label);
        String[] items = new String[totalDays];
        for (int i = 0; i < totalDays; i++) {
            items[i] = "Day " + (i + 1);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        container.addView(newView, container.getChildCount());
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
        postDetails.clear();
        int count = container.getChildCount();
        View v;

        String id = ref.push().getKey();
        String location = etLocation.getText().toString();
        String hotel = etHotel.getText().toString();
        int days = Integer.parseInt(etDays.getText().toString());
        int totalCost = Integer.parseInt(etTotalCost.getText().toString());
        int ticketPrice = Integer.parseInt(etTicketPrice.getText().toString());
        int costPerNight = Integer.parseInt(etCostPerNight.getText().toString());
        boolean roundtrip = roundTrip.isChecked();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteFormat = stream.toByteArray();
        String encodedImage = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

        for (int i = 0; i < count; i++) {
            v = container.getChildAt(i);

            Spinner spinner = v.findViewById(R.id.label);
            EditText etDestination = v.findViewById(R.id.input_destination);
            EditText etCost = v.findViewById(R.id.input_cost);
            EditText etReview = v.findViewById(R.id.input_review);
            RatingBar ratingBar = v.findViewById(R.id.rating);
            ImageView loc = v.findViewById(R.id.locationImg);

            String label = spinner.getSelectedItem().toString();
            String destination = etDestination.getText().toString();
            int cost = Integer.parseInt(etCost.getText().toString());
            String review = etReview.getText().toString();
            float rating = ratingBar.getRating();

            BitmapDrawable drawable = (BitmapDrawable) loc.getDrawable();
            Bitmap decodedByte2 = drawable.getBitmap();
            ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
            decodedByte2.compress(Bitmap.CompressFormat.JPEG, 100, stream2);
            byte[] byteFormat2 = stream2.toByteArray();
            String encodedImage2 = Base64.encodeToString(byteFormat2, Base64.NO_WRAP);

            postDetails.add(new Details(label, cost, destination, review, rating, encodedImage2));
        }

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        String username = currentUser.getDisplayName();

        Post post = new Post(id, username, encodedImage, location, hotel, days, totalCost, ticketPrice, costPerNight, roundtrip, postDetails);

        assert id != null;
        ref.child(id).setValue(post);

        Intent intent = new Intent(AddPostDetails.this, Home.class);
        this.startActivity(intent);
    }
}