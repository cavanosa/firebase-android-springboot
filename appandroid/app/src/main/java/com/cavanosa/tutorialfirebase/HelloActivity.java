package com.cavanosa.tutorialfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import de.hdodenhof.circleimageview.CircleImageView;

public class HelloActivity extends AppCompatActivity {

    CircleImageView profileImage;
    TextView helloText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
        helloText = findViewById(R.id.helloText);
        profileImage = findViewById(R.id.profile_image);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser != null) {
            Glide
                    .with(this)
                    .load(currentUser.getPhotoUrl().toString())
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(profileImage);
        }

        findViewById(R.id.sign_out_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });
        currentUser.getIdToken(true)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    @Override
                    public void onComplete(Task<GetTokenResult> task) {
                        String token = task.getResult().getToken();
                        Log.i("token", token);
                    }
                });
    }

    public void signOut() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(HelloActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}