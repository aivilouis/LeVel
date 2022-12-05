package umn.ac.id.level;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SignUp extends AppCompatActivity {

    private EditText etUsername, etEmail, etPassword;
    private FirebaseAuth mAuth;

    FirebaseDatabase rootNode;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

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

        String id, username, email, password;
//        id = ref.push().getKey();
        id = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        username = etUsername.getText().toString();
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(getApplicationContext(), "Please enter username", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SignUp.this, Account.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Registration failed." + " Please try again later", Toast.LENGTH_LONG).show();
                    }
                });

        UserData user = new UserData(id, username, email);

        ref.child(id).setValue(user);
    }
}