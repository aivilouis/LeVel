package umn.ac.id.level;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;


public class SignUp extends AppCompatActivity
{

    EditText etEmail, etPassword;
    FirebaseAuth mAuth;

    FirebaseDatabase rootNode;
    DatabaseReference ref;

    SharedPreferences sharedPreferences;
    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Objects.requireNonNull(getSupportActionBar()).hide();

        mAuth = FirebaseAuth.getInstance();

        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);
        Button btn = findViewById(R.id.signupBtn);
        TextView tvSignIn = findViewById(R.id.tvSignIn);

        sharedPreferences = getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE);

        email = sharedPreferences.getString("EMAIL_KEY", null);
        password = sharedPreferences.getString("PASSWORD_KEY", null);

        btn.setOnClickListener(v -> registerNewUser());
        tvSignIn.setOnClickListener(v ->
        {
            Intent intent = new Intent(SignUp.this, Login.class);
            startActivity(intent);
        });
    }

    private void registerNewUser()
    {
        rootNode = FirebaseDatabase.getInstance("https://level-fecbd-default-rtdb.asia-southeast1.firebasedatabase.app/");
        ref = rootNode.getReference("Users");

        String mEmail, mPassword;
        mEmail = etEmail.getText().toString();
        mPassword = etPassword.getText().toString();

        if (etEmail.length() == 0)
        {
            etEmail.setError("Please enter your email");
            return;
        }
        if (etPassword.length() == 0)
        {
            etPassword.setError("Please enter your password");
            return;
        }
        if (etPassword.length() > 0 && etPassword.length() < 8)
        {
            etPassword.setError("Password must be at least 8 characters");
            return;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("EMAIL_KEY", mEmail);
        editor.putString("PASSWORD_KEY", mPassword);
        editor.apply();

        mAuth.createUserWithEmailAndPassword(mEmail, mPassword)
                .addOnCompleteListener(task ->
                {
                    if (task.isSuccessful())
                    {
                        Intent intent = new Intent(SignUp.this, CompleteProfile.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Registration failed." +
                                " Please try again later", Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void ShowHidePass(View view)
    {
        if(view.getId()==R.id.show_pass_btn)
        {
            if(etPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance()))
            {
                ((ImageView)(view)).setImageResource(R.drawable.icon_closeye);
                // Show Password
                etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else
            {
                ((ImageView)(view)).setImageResource(R.drawable.icon_eye);
                // Hide Password
                etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }

}
