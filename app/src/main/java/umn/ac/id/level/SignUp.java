package umn.ac.id.level;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    private EditText etUsername, etEmail, etPassword;
    private FirebaseAuth mAuth;

    FirebaseDatabase rootNode;
    DatabaseReference ref;

    SharedPreferences sharedPreferences;
    String email, password, uid, username;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        sharedPreferences = getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE);

        email = sharedPreferences.getString("EMAIL_KEY", null);
        password = sharedPreferences.getString("PASSWORD_KEY", null);
        uid = sharedPreferences.getString("UID", null);
        username = sharedPreferences.getString("USERNAME", null);

        etUsername = findViewById(R.id.username);
        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);
        Button btn = findViewById(R.id.signupBtn);
        TextView tvSignIn = findViewById(R.id.tvSignIn);

        btn.setOnClickListener(v -> registerNewUser());

        tvSignIn.setOnClickListener(v -> {
            Intent intent = new Intent(SignUp.this, Login.class);
            startActivity(intent);
        });
    }

    private void registerNewUser()
    {
        rootNode = FirebaseDatabase.getInstance("https://level-fecbd-default-rtdb.asia-southeast1.firebasedatabase.app/");
        ref = rootNode.getReference("Users");

        String id, mUsername, mEmail, mPassword;
        id = ref.push().getKey();
        mUsername = etUsername.getText().toString();
        mEmail = etEmail.getText().toString();
        mPassword = etPassword.getText().toString();

        if (TextUtils.isEmpty(mUsername)) {
            Toast.makeText(getApplicationContext(), "Please enter username", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(mEmail)) {
            Toast.makeText(getApplicationContext(), "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(mPassword)) {
            Toast.makeText(getApplicationContext(), "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("EMAIL_KEY", mEmail);
        editor.putString("PASSWORD_KEY", mPassword);
        editor.putString("UID", id);
        editor.putString("USERNAME", mUsername);
        editor.apply();

        mAuth.createUserWithEmailAndPassword(mEmail, mPassword)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SignUp.this, Account.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Registration failed." + " Please try again later", Toast.LENGTH_LONG).show();
                    }
                });

        UserData user = new UserData(id, mUsername, mEmail);

        assert id != null;
        ref.child(id).setValue(user);
    }
}