package umn.ac.id.level;

import static java.lang.Integer.parseInt;

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
import android.widget.Toast;

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
    Button addPhotoBtn;
    ArrayList<Integer> sum = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post_details);

        // Set custom actionbar
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.addpost_actionbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.cancel_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Database
        rootNode = FirebaseDatabase.getInstance("https://level-fecbd-default-rtdb.asia-southeast1.firebasedatabase.app/");

        // Get intent
        Intent intent = getIntent();

        // Get image view
        ImageView img = findViewById(R.id.imgView);

        // Set image view
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

        // Get view
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

        // Activity result launcher for gallery intent
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();

                        assert data != null;
                        locationUri = data.getData();
                        locationImg.setVisibility(View.VISIBLE);
                        locationImg.setImageURI(locationUri);

                        if (locationUri != null) {
                            addPhotoBtn.setVisibility(View.GONE);
                        }
                    }
                }
        );
    }

    private void addNewView() {
        // Check for empty field
        if (etLocation.length() == 0) {
            etLocation.setError("This field is required");
            return;
        }

        if (etDays.length() == 0) {
            etDays.setError("This field is required");
            return;
        }
        if (Integer.parseInt(etDays.getText().toString()) == 0) {
            etDays.setError("Travel Days must be more than 0");
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

        // Get user input
        int totalDays = parseInt(etDays.getText().toString());
        int curTotalCost = Integer.parseInt(etTotalCost.getText().toString());
        int curTicketPrice = Integer.parseInt(etTicketPrice.getText().toString());
        int curCostPerNight = Integer.parseInt(etCostPerNight.getText().toString());

        // Check cost value
        if (curTicketPrice > curTotalCost) {
            etTicketPrice.setError("Total cost must not exceeds " +
                    "Rp " + etTotalCost.getText().toString() + ",-");
            return;
        }

        if (curCostPerNight > curTotalCost || curCostPerNight + curTicketPrice > curTotalCost) {
            etCostPerNight.setError("Total cost must not exceeds " +
                    "Rp " + etTotalCost.getText().toString() + ",-");
            return;
        }

        // Set new view
        newView = LayoutInflater.from(this).inflate(R.layout.items, container, false);
        locationImg = newView.findViewById(R.id.locationImg);
        addPhotoBtn = newView.findViewById(R.id.addPhotoBtn);

        // Add photo button OnClick
        addPhotoBtn.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            activityResultLauncher.launch(intent);
        });

        // Set spinner items
        Spinner dropdown = newView.findViewById(R.id.label);

        String[] items = new String[totalDays];
        for (int i = 0; i < totalDays; i++) {
            items[i] = "Day " + (i + 1);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        // Input validation for new view
        if (container.getChildCount() > 0) {
            EditText dest = container.getChildAt(container.getChildCount()-1).findViewById(R.id.input_destination);
            EditText cost = container.getChildAt(container.getChildCount()-1).findViewById(R.id.input_cost);
            EditText review = container.getChildAt(container.getChildCount()-1).findViewById(R.id.input_review);
            ImageView imgView = container.getChildAt(container.getChildCount()-1).findViewById(R.id.locationImg);
            BitmapDrawable bm = (BitmapDrawable) imgView.getDrawable();

            // Check for empty field
            if (dest.length() == 0) {
                dest.setError("This field is required");
                return;
            }

            if (cost.length() == 0) {
                cost.setError("This field is required");
                return;
            }

            if (review.length() == 0) {
                review.setError("This field is required");
                return;
            }

            if (bm == null) {
                Toast.makeText(AddPostDetails.this, "Please add image", Toast.LENGTH_LONG).show();
                return;
            }

            // Check cost value
            int currentCost = Integer.parseInt(cost.getText().toString());
            sum.add(currentCost);
            int total = curTicketPrice + curCostPerNight;

            for (int c : sum) {
                total += c;
                if (total > curTotalCost) {
                    cost.setError("Total cost must not exceeds " +
                            "Rp " + etTotalCost.getText().toString() + ",-");
                }
            }
        }

        // Add new view
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

    // Save data
    private void saveData() {

        // Get database reference
        ref = rootNode.getReference("Posts");
        postDetails.clear();

        int count = container.getChildCount();
        View v;

        // Check post details input
        if (count < 1 || count < Integer.parseInt(etDays.getText().toString())) {
            Toast.makeText(AddPostDetails.this,
                    "Please add more details",
                    Toast.LENGTH_LONG).show();
            return;
        }

        // Check for empty field
        if (etLocation.length() == 0) {
            etLocation.setError("This field is required");
            return;
        }

        if (etDays.length() == 0) {
            etDays.setError("This field is required");
            return;
        }

        if (Integer.parseInt(etDays.getText().toString()) == 0) {
            etDays.setError("Travel Days must be more than 0");
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

        // Generate ID
        String id = ref.push().getKey();

        // Get user input
        String location = etLocation.getText().toString().toLowerCase();
        String hotel = etHotel.getText().toString();
        int days = parseInt(etDays.getText().toString());
        int totalCost = parseInt(etTotalCost.getText().toString());
        int ticketPrice = parseInt(etTicketPrice.getText().toString());
        int costPerNight = parseInt(etCostPerNight.getText().toString());
        boolean roundtrip = roundTrip.isChecked();

        // Encode image to Base64 string
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteFormat = stream.toByteArray();
        String encodedImage = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

        // Get user input from new view
        for (int i = 0; i < count; i++) {
            v = container.getChildAt(i);

            Spinner spinner = v.findViewById(R.id.label);
            EditText etDestination = v.findViewById(R.id.input_destination);
            EditText etCost = v.findViewById(R.id.input_cost);
            EditText etReview = v.findViewById(R.id.input_review);
            RatingBar ratingBar = v.findViewById(R.id.rating);
            ImageView loc = v.findViewById(R.id.locationImg);

            // Check for empty field
            if (etDestination.length() == 0) {
                etDestination.setError("This field is required");
                return;
            }

            if (etCost.length() == 0) {
                etCost.setError("This field is required");
                return;
            }

            if (etReview.length() == 0) {
                etReview.setError("This field is required");
                return;
            }

            String label = spinner.getSelectedItem().toString();
            String destination = etDestination.getText().toString();
            int cost = parseInt(etCost.getText().toString());
            String review = etReview.getText().toString();
            float rating = ratingBar.getRating();

            BitmapDrawable drawable = (BitmapDrawable) loc.getDrawable();

            if (drawable == null) {
                Toast.makeText(AddPostDetails.this, "Please add image", Toast.LENGTH_LONG).show();
                return;
            } else {
                Bitmap decodedByte2 = drawable.getBitmap();
                ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
                decodedByte2.compress(Bitmap.CompressFormat.JPEG, 100, stream2);
                byte[] byteFormat2 = stream2.toByteArray();
                String encodedImage2 = Base64.encodeToString(byteFormat2, Base64.NO_WRAP);

                postDetails.add(new Details(label, cost, destination, review, rating, encodedImage2));
            }
        }

        // Get current user's username
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        String username = currentUser.getDisplayName();

        Post post = new Post(id, username, encodedImage, location, hotel, days, totalCost, ticketPrice, costPerNight, roundtrip, postDetails);

        // Add to database
        assert id != null;
        ref.child(id).setValue(post);

        // Move to Home activity
        Intent intent = new Intent(AddPostDetails.this, Home.class);
        this.startActivity(intent);
    }
}