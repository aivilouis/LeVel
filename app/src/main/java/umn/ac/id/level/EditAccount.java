package umn.ac.id.level;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class EditAccount extends AppCompatActivity {

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.editaccount_actionbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.cancel_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EditText username = findViewById(R.id.input_username);
        EditText country = findViewById(R.id.input_country);
        EditText category = findViewById(R.id.input_category);
        EditText bio = findViewById(R.id.input_bio);

        username.setText("han.sohee");
        username.setSelection(username.getText().length());
        country.setText("South Korea");
        country.setSelection(country.getText().length());
        category.setText("Business Traveler");
        category.setSelection(category.getText().length());
        bio.setText("안녕하십니까");
        bio.setSelection(bio.getText().length());

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
            Intent intent = new Intent(EditAccount.this, Account.class);
            this.startActivity(intent);
            return true;
        }
        if (id == android.R.id.home) {
            Intent intent = new Intent(EditAccount.this, Account.class);
            this.startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
