package umn.ac.id.level;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Login extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private FirebaseAuth mAuth;

    FirebaseDatabase rootNode;
    DatabaseReference ref;

    SharedPreferences sharedPreferences;
    String email, password, uid, username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        rootNode = FirebaseDatabase.getInstance("https://level-fecbd-default-rtdb.asia-southeast1.firebasedatabase.app/");
        ref = rootNode.getReference("Users");

        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);
        Button btn = findViewById(R.id.loginBtn);
        TextView tvSignup = findViewById(R.id.tvSignup);

        sharedPreferences = getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE);

        email = sharedPreferences.getString("EMAIL_KEY", null);
        password = sharedPreferences.getString("PASSWORD_KEY", null);
        uid = sharedPreferences.getString("UID", null);
        username = sharedPreferences.getString("USERNAME", null);

        btn.setOnClickListener(v -> loginUserAccount());

        tvSignup.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, SignUp.class);
            startActivity(intent);
        });
    }

    private void loginUserAccount() {
        String mEmail;
        String mPassword;
        final String[] id = new String[1];
        final String[] uname = new String[1];
        mEmail = etEmail.getText().toString();
        mPassword = etPassword.getText().toString();

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getKey();
                    if (Objects.requireNonNull(ds.child("email").getValue(String.class)).contentEquals(mEmail)) {
                        id[0] = key;
                        uname[0] = ds.child("username").getValue(String.class);
                        Log.d("TEST", id[0]);
                        Log.d("TEST", uname[0]);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        };

        ref.addListenerForSingleValueEvent(eventListener);

        if (TextUtils.isEmpty(mEmail) || TextUtils.isEmpty(mPassword)) {
            Toast.makeText(getApplicationContext(), "This field is required", Toast.LENGTH_LONG).show();
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("EMAIL_KEY", mEmail);
            editor.putString("PASSWORD_KEY", mPassword);
            editor.putString("UID", id[0]);
            editor.putString("USERNAME", uname[0]);
            editor.apply();

            mAuth.signInWithEmailAndPassword(mEmail, mPassword).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Login.this, Account.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (email != null && password != null) {
            Intent intent = new Intent(Login.this, Home.class);
            startActivity(intent);
        }
    }
}