package umn.ac.id.level;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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

public class EditAccount extends AppCompatActivity {

    ImageView profPic;
    EditText username, bio;
    CountryCodePicker country;
    Spinner category;
    Uri uri;
    Bitmap bitmap, decodedByte;

    String currentUname;

    FirebaseDatabase rootNode;
    DatabaseReference ref;

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        // Set custom actionbar
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.editaccount_actionbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.cancel_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get current user's username
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        currentUname = user.getDisplayName();

        // Database
        rootNode = FirebaseDatabase.getInstance("https://level-fecbd-default-rtdb.asia-southeast1.firebasedatabase.app/");
        ref = rootNode.getReference("UserData");

        // Set category spinner items
        category = findViewById(R.id.input_category);
        String[] items = new String[]{"Business Traveler", "Leisure Traveler", "Special Interest Traveler"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        category.setAdapter(adapter);

        // Get view
        username = findViewById(R.id.input_username);
        profPic = findViewById(R.id.profilePic);
        country = findViewById(R.id.input_country);
        bio = findViewById(R.id.input_bio);

        // Set view based on user data in database
        ValueEventListener dataListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserData userData = dataSnapshot.getValue(UserData.class);

                assert userData != null;
                username.setText(userData.getUsername());
                country.setDefaultCountryUsingNameCode(userData.getCountryCode());
                country.resetToDefaultCountry();
                category.setSelection(userData.getCategoryId());
                bio.setText(userData.getBio());

                // Decode image
                byte[] decodedString = Base64.decode(userData.getProfPic(), Base64.DEFAULT);
                decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                profPic.setImageBitmap(decodedByte);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        };

        ref.child(currentUname).addValueEventListener(dataListener);

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

                        // Update user data
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setPhotoUri(uri)
                                .build();
                        user.updateProfile(profileUpdates);
                    }
                }
        );

        // Edit profile button OnClick
        Button editProfile = findViewById(R.id.editProfileBtn);

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
            updateData();
            return true;
        }

        if (id == android.R.id.home) {
            Intent intent = new Intent(EditAccount.this, Account.class);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateData() {

        // Encode image to Base64 string
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        if (bitmap != null){
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        } else {
            decodedByte.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        }

        byte[] byteFormat = stream.toByteArray();
        String encodedImage = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

        // Get user input
        String mUsername = username.getText().toString();
        String mCountry = country.getSelectedCountryName();
        String countryCode = country.getSelectedCountryNameCode();
        int categoryId = category.getSelectedItemPosition();
        String mCategory = category.getSelectedItem().toString();
        String mBio = bio.getText().toString();

        // Get current user's username
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        String username = currentUser.getDisplayName();

        // Update user data
        assert username != null;
        ref.child(username).setValue(new UserData(encodedImage, mUsername, countryCode, mCountry, categoryId, mCategory, mBio));

        // Move to Account activity
        Intent intent = new Intent(EditAccount.this, Account.class);
        this.startActivity(intent);
    }
}
