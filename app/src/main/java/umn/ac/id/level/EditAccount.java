package umn.ac.id.level;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.util.Objects;

public class EditAccount extends AppCompatActivity {

    EditText username, bio;
    CountryCodePicker country;
    Spinner category;

    String currentUname;

    FirebaseDatabase rootNode;
    DatabaseReference ref;

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.editaccount_actionbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.cancel_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        currentUname = user.getDisplayName();

        rootNode = FirebaseDatabase.getInstance("https://level-fecbd-default-rtdb.asia-southeast1.firebasedatabase.app/");
        ref = rootNode.getReference("UserData");

        category = findViewById(R.id.input_category);
        String[] items = new String[]{"Business Traveler", "Leisure Traveler"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        category.setAdapter(adapter);

        username = findViewById(R.id.input_username);
        country = findViewById(R.id.input_country);
        bio = findViewById(R.id.input_bio);

        ValueEventListener dataListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserData userData = dataSnapshot.getValue(UserData.class);
                assert userData != null;
                username.setText(userData.getUsername());
                category.setSelection(userData.getCategoryId());
                bio.setText(userData.getBio());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        };

        ref.child(currentUname).addValueEventListener(dataListener);

        Button editProfile = findViewById(R.id.editprofileBtn);
        editProfile.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(android.content.Intent.ACTION_VIEW);
            intent.setType("image/*");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
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
        String mUsername = username.getText().toString();
        String mCountry = country.getSelectedCountryName();
//        int flagId = country.getSelectedCountryFlagResourceId();
        int categoryId = category.getSelectedItemPosition();
        String mCategory = category.getSelectedItem().toString();
        String mBio = bio.getText().toString();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        String username = currentUser.getDisplayName();

        assert username != null;
        ref.child(username).setValue(new UserData(mUsername, mCountry, categoryId, mCategory, mBio));

        Intent intent = new Intent(EditAccount.this, Account.class);
        this.startActivity(intent);
    }
}
