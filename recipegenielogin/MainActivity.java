// IM/2021/020 - M.A.P.M Karunathilaka

package com.example.recipegenie;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
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
    private static final int RC_SIGN_IN = 9001; // Request code for Google Sign-In
    private Button  buttonLogin;
    private TextView forgotPassword;
    private EditText editTextEmail, editTextPassword;
    private FirebaseAuth mAuth;
    private com.google.android.gms.common.SignInButton googleSignInButton;
    private GoogleSignInClient mGoogleSignInClient;
    private TextView signup;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        signup = findViewById(R.id.textViewSignUp);
        forgotPassword = findViewById(R.id.forgotPasswordText);
        buttonLogin = findViewById(R.id.buttonLogin);
        editTextEmail = findViewById(R.id.emailEdit);
        editTextPassword = findViewById(R.id.passwordEditText1);
        googleSignInButton = findViewById(R.id.google_sign_in_button); // Find the Google Sign-In button

        // Configure Google Sign-In options
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))  // Replace with your actual client ID from Firebase
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Set up Google Sign-In button
        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInWithGoogle();
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                // Validate user input
                if (TextUtils.isEmpty(email)) {
                    editTextEmail.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    editTextPassword.setError("Password is required");
                    return;
                }
                if (password.length() < 6) {
                    editTextPassword.setError("Password must be at least 6 characters");
                    return;
                }

                // Sign in the user using Firebase Authentication
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Login.this, task -> {
                            if (task.isSuccessful()) {
                                // Sign-in success, navigate to Home
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();

                                // Redirect to Home
                                Intent intent = new Intent(Login.this, Home.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // If sign-in fails, display a message to the user
                                Toast.makeText(Login.this, "Authentication failed: " + task.getException().getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to signup activity
                Intent intent = new Intent(Login.this, signup.class);
                startActivity(intent);
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to ForgotPassword activity
                Intent intent = new Intent(Login.this, Forgot_Password.class);
                startActivity(intent);
            }
        });
    }

    // Method to start Google Sign-In process
    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign-In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    firebaseAuthWithGoogle(account.getIdToken());
                }
            } catch (ApiException e) {
                Toast.makeText(Login.this, "Google sign in failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Authenticate with Firebase using Google ID token
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign-in success, update UI with the signed-in user's information
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(Login.this, "Google sign-in successful", Toast.LENGTH_SHORT).show();

                        // Redirect to Home
                        Intent intent = new Intent(Login.this, Home.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // If sign-in fails, display a message to the user
                        Toast.makeText(Login.this, "Firebase Authentication failed: " + task.getException().getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }
}
// IM/2021/020 - M.A.P.M Karunathilaka
