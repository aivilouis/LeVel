package umn.ac.id.level;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Login extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private FirebaseAuth mAuth;
    SharedPreferences sharedPreferences;
    String email, password;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);
        Button btn = findViewById(R.id.loginBtn);
        TextView googleSignin = findViewById(R.id.googleSignIn);
        TextView tvSignup = findViewById(R.id.tvSignup);

        sharedPreferences = getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE);

        email = sharedPreferences.getString("EMAIL_KEY", null);
        password = sharedPreferences.getString("PASSWORD_KEY", null);

        btn.setOnClickListener(v -> loginUserAccount());

        tvSignup.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, SignUp.class);
            startActivity(intent);
        });

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(Login.this, googleSignInOptions);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);

                        if (signInAccountTask.isSuccessful()) {
                            String s = "Google sign in successful";
                            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                            try {
                                GoogleSignInAccount googleSignInAccount = signInAccountTask.getResult(ApiException.class);
                                if (googleSignInAccount != null) {
                                    AuthCredential authCredential= GoogleAuthProvider
                                            .getCredential(googleSignInAccount.getIdToken(), null);

                                    mAuth.signInWithCredential(authCredential)
                                            .addOnCompleteListener(this, task -> {
                                                if(task.isSuccessful()) {
                                                    startActivity(new Intent(Login.this, Account.class)
                                                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                                    Toast.makeText(getApplicationContext(),
                                                            "Firebase authentication successful", Toast.LENGTH_SHORT).show();
                                                }
                                                else {
                                                    Toast.makeText(getApplicationContext(),
                                                            "Firebase authentication failed", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                }
                            } catch (ApiException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );

        googleSignin.setOnClickListener(view -> {
            Intent intent = mGoogleSignInClient.getSignInIntent();
            activityResultLauncher.launch(intent);
        });
    }

    private void loginUserAccount() {
        String mEmail, mPassword;
        mEmail = etEmail.getText().toString();
        mPassword = etPassword.getText().toString();

        if (TextUtils.isEmpty(mEmail) || TextUtils.isEmpty(mPassword)) {
            Toast.makeText(getApplicationContext(), "This field is required", Toast.LENGTH_LONG).show();
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("EMAIL_KEY", mEmail);
            editor.putString("PASSWORD_KEY", mPassword);
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
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            startActivity(new Intent(Login.this, Account.class));
        } else if (email != null && password != null) {
            Intent intent = new Intent(Login.this, Home.class);
            startActivity(intent);
        }
    }
}