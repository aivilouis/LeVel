package umn.ac.id.level;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

public class CompleteProfile extends AppCompatActivity {

    ImageView profPic;
    EditText username, bio;
    CountryCodePicker country;
    Spinner category;
    Uri uri;
    Bitmap bitmap, decodedByte;

    FirebaseDatabase rootNode;
    DatabaseReference ref;
    FirebaseUser user;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);

        // Set custom actionbar
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.complete_profile_actionbar);
        getSupportActionBar().setElevation(0);

        // Shared preference
        sharedPreferences = getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE);

        // Get current user
        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;

        // Database
        rootNode = FirebaseDatabase.getInstance("https://level-fecbd-default-rtdb.asia-southeast1.firebasedatabase.app/");
        ref = rootNode.getReference("UserData");

        // Set category spinner items
        category = findViewById(R.id.input_category);
        String[] items = new String[]{"Business Traveler", "Leisure Traveler", "Special Interest Traveler"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        category.setAdapter(adapter);

        username = findViewById(R.id.input_username);

        // Set default profile picture
        profPic = findViewById(R.id.profilePic);
        new ImageLoadTask("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png",
                profPic).execute();

        country = findViewById(R.id.input_country);
        bio = findViewById(R.id.input_bio);

        // Activity result launcher for gallery intent
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        assert data != null;
                        uri = data.getData();
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        profPic.setImageURI(uri);
                    }
                }
        );

        // Edit profile button OnClick
        Button editProfile = findViewById(R.id.changeProfPic);

        editProfile.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            activityResultLauncher.launch(intent);
        });
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
        return super.onOptionsItemSelected(item);
    }

    // Email verification
    private void sendEmailVerification() {

        // Get current user
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        // Send verification email
        assert firebaseUser != null;
        firebaseUser.sendEmailVerification()
                .addOnSuccessListener(unused -> Log.d(TAG, "Verification email sent"))
                .addOnFailureListener(e ->
                        Toast.makeText(CompleteProfile.this,
                                "Failed to send verification email due to " + e.getMessage(),
                                Toast.LENGTH_SHORT).show());
    }

    // Save user data
    private void saveData() {

        // Check for empty field
        if (username.length() == 0) {
            username.setError("Please enter your username");
            return;
        }

        if (bio.length() == 0) {
            bio.setText(" ");
        }

        // Get user input
        String mUsername = username.getText().toString();

        // Check username availability
        ref.child(mUsername).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    username.setError("Username already exists");
                } else {
                    continueSignup(username.getText().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    private void continueSignup(String mUsername) {

        // Encode image to Base64 string
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        if (bitmap != null) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        } else {
            BitmapDrawable drawable = (BitmapDrawable) profPic.getDrawable();
            decodedByte = drawable.getBitmap();
            decodedByte.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        }
        byte[] byteFormat = stream.toByteArray();
        String encodedImage = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

        // Get user input
        String mCountry = country.getSelectedCountryName();
        String countryCode = country.getDefaultCountryNameCode();
        int categoryId = category.getSelectedItemPosition();
        String mCategory = category.getSelectedItem().toString();
        String mBio = bio.getText().toString();

        // Add user to database
        ref.child(mUsername).setValue(new UserData(encodedImage, mUsername, countryCode, mCountry, categoryId, mCategory, mBio));

        // Set user display name
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(mUsername)
                .build();

        // Send verification email
        user.updateProfile(profileUpdates).addOnCompleteListener(task1 -> {
            if (task1.isSuccessful()) {
                sendEmailVerification();
                Toast.makeText(getApplicationContext(),
                        "Sign Up successful. Please verify your email",
                        Toast.LENGTH_LONG).show();

                // Sign out
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                FirebaseAuth.getInstance().signOut();

                // Move to Login activity
                Intent intent = new Intent(CompleteProfile.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}