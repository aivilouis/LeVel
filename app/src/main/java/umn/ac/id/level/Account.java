package umn.ac.id.level;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Account extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;

        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.account_actionbar);
        getSupportActionBar().setElevation(0);

        TextView username = getSupportActionBar().getCustomView().findViewById(R.id.accountUsername);
        username.setText(user.getDisplayName());

        sharedPreferences = getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE);

//        Button editBtn = findViewById(R.id.editprofileBtn);
//        editBtn.setOnClickListener(v -> {
//            Intent intent = new Intent(Account.this, EditAccount.class);
//            this.startActivity(intent);
//        });

        RecyclerView mRecyclerView = findViewById(R.id.recyclerview2);
        List<Posts> items = new ArrayList<>();
        items.add(new Posts(R.drawable.bali, R.drawable.bali, R.drawable.bali));
        items.add(new Posts(R.drawable.bali, R.drawable.bali, R.drawable.bali));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new AccountAdapter(getApplicationContext(),items));

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.action_account);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch(item.getItemId()) {
                case R.id.action_home:
                    startActivity(new Intent(getApplicationContext(), Home.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.action_addpost:
                    startActivity(new Intent(getApplicationContext(), AddPost.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.action_save:
                    startActivity(new Intent(getApplicationContext(), Saved.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.action_account:
                    return true;
            }
            return false;
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_account, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                Intent intentEdit = new Intent(Account.this, EditAccount.class);
                startActivity(intentEdit);
                return true;
            case R.id.faq:
                Intent intentFAQ = new Intent(Account.this, FAQ.class);
                startActivity(intentFAQ);
                return true;
            case R.id.about:
                Intent intentAbout = new Intent(Account.this, About.class);
                startActivity(intentAbout);
                return true;
            case R.id.signout:
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                Intent i = new Intent(Account.this, Login.class);
                startActivity(i);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}