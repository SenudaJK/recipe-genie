package com.example.recipegenie;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

// IM/2021/009 - Y.A.D.S.C.BASNAYAKE
public class EditProfile extends AppCompatActivity {

        private UserModel currentUserModel;

        private ImageView backIcon;
        private TextInputEditText emailTextInputEditText, nameTextInputEditText;
        private Button save_button;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_edit_profile);

            // initialize views
            backIcon = findViewById(R.id.backIcon);
            emailTextInputEditText = findViewById(R.id.editEmail);
            nameTextInputEditText = findViewById(R.id.editName);
            save_button = findViewById(R.id.save_button);

            // fetch current user data from Firebase
            getUserData();
                
            save_button.setOnClickListener(v -> updateBtnClick());

            // Back button 
            backIcon.setOnClickListener(v -> {
                Intent intent = new Intent(EditProfile.this, Profile.class);
                startActivity(intent);
            });
        }

        // to handle the save button click and update the username
        void updateBtnClick() {
            String newUserName = Objects.requireNonNull(nameTextInputEditText.getText()).toString();

            // Update only if the username is valid (non-empty)
            if (!newUserName.isEmpty()) {
                currentUserModel.setUserName(newUserName);
                updateToFirebase();
            } else {
                Toast.makeText(this, "Please enter a valid username", Toast.LENGTH_SHORT).show();
            }
        }

        // update the user data in Firebase realtime db
        void updateToFirebase() {
            FirebaseUtil.currentUserDetails().setValue(currentUserModel)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Updated successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        // fetch the current user data from Firebase
        void getUserData() {
            FirebaseUtil.currentUserDetails().get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    currentUserModel = task.getResult().getValue(UserModel.class);

                    // Set email as non-editable and pre-fill fields
                    emailTextInputEditText.setText(Objects.requireNonNull(currentUserModel).getUserEmail());
                    emailTextInputEditText.setEnabled(false);  // make email non-editable
                    nameTextInputEditText.setText(currentUserModel.getUserName());
                } else {
                    Toast.makeText(this, "Failed to fetch user data", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

