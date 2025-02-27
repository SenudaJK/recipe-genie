package com.example.getting_started;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

// IM/2021/009 - Y.A.D.S.C.Basnayake 
public class GetStarted extends Activity {
    private Button buttonGet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started); 
        buttonGet = findViewById(R.id.buttonGet);

        buttonGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to Forgot_Password activity
                Intent intent = new Intent(GetStarted.this, Forgot_Password.class);
                startActivity(intent);
            }
        });
}
}
