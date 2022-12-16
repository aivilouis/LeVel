package umn.ac.id.level;

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

        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.editaccount_actionbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.cancel_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences = getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE);

        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;

        rootNode = FirebaseDatabase.getInstance("https://level-fecbd-default-rtdb.asia-southeast1.firebasedatabase.app/");
        ref = rootNode.getReference("UserData");

        category = findViewById(R.id.input_category);
        String[] items = new String[]{"Business Traveler", "Leisure Traveler"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        category.setAdapter(adapter);

        username = findViewById(R.id.input_username);

        profPic = findViewById(R.id.profilePic);
        new ImageLoadTask("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png",
                profPic).execute();

        country = findViewById(R.id.input_country);
        bio = findViewById(R.id.input_bio);

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
        if (id == android.R.id.home) {
            Intent intent = new Intent(CompleteProfile.this, SignUp.class);
            this.startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveData() {

        if (username.length() == 0) {
            username.setError("This field is required");
            return;
        }
        if (bio.length() == 0) {
            bio.setError("This field is required");
            return;
        }

        String mUsername = username.getText().toString();

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


        String mCountry = country.getSelectedCountryName();
        //        int flagId = country.getSelectedCountryFlagResourceId();
        int categoryId = category.getSelectedItemPosition();
        String mCategory = category.getSelectedItem().toString();
        String mBio = bio.getText().toString();

        ref.child(mUsername).setValue(new UserData(encodedImage, mUsername, mCountry, categoryId, mCategory, mBio));

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(mUsername)
                .build();

        user.updateProfile(profileUpdates).addOnCompleteListener(task1 -> {
            if (task1.isSuccessful()) {
                Toast.makeText(getApplicationContext(),
                        "Sign Up successful. Please verify your email",
                        Toast.LENGTH_LONG).show();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(CompleteProfile.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}