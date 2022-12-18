package umn.ac.id.level;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {

    EditText etEmail, etPassword;
    TextView tvForgotPass;
    FirebaseAuth mAuth;
    SharedPreferences sharedPreferences;
    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();

        tvForgotPass = findViewById(R.id.forgotPassword);
        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);
        Button btn = findViewById(R.id.loginBtn);
        TextView tvSignup = findViewById(R.id.tvSignup);

        sharedPreferences = getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE);

        email = sharedPreferences.getString("EMAIL_KEY", null);
        password = sharedPreferences.getString("PASSWORD_KEY", null);

        btn.setOnClickListener(v -> loginUserAccount());

        tvSignup.setOnClickListener(v ->
        {
            Intent intent = new Intent(Login.this, SignUp.class);
            startActivity(intent);
        });

        tvForgotPass.setOnClickListener(v -> sendResetPassEmail());
    }

    private void loginUserAccount()
    {
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

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("EMAIL_KEY", mEmail);
        editor.putString("PASSWORD_KEY", mPassword);
        editor.apply();

        mAuth.signInWithEmailAndPassword(mEmail, mPassword).addOnCompleteListener(task ->
        {
            if (task.isSuccessful())
            {
                checkIfEmailVerified();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onStart()
    {
        super.onStart();

        if (email != null && password != null)
        {
            Intent intent = new Intent(Login.this, Home.class);
            startActivity(intent);
        }
    }

    private void checkIfEmailVerified()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        assert user != null;
        if (user.isEmailVerified())
        {
            Toast.makeText(Login.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Login.this, Account.class);
            startActivity(intent);
            finish();
        } else
        {
            Toast.makeText(Login.this, "Login failed. Please verify your email.", Toast.LENGTH_LONG).show();

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            FirebaseAuth.getInstance().signOut();

            Login.this.recreate();
        }
    }

    private void sendResetPassEmail()
    {
        mAuth = FirebaseAuth.getInstance();

        if (etEmail.length() == 0)
        {
            etEmail.setError("Please enter your email");
        } else
        {
            String emailAddress = etEmail.getText().toString();

            mAuth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener(task ->
                    {
                        if (task.isSuccessful())
                        {
                            Log.d(TAG, "Reset password email sent");
                        }
                    });
        }
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