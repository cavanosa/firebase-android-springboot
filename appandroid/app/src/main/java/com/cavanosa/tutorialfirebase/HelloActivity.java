package com.cavanosa.tutorialfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cavanosa.tutorialfirebase.dto.MessageDto;
import com.cavanosa.tutorialfirebase.interfaces.HelloInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
                        HelloInterface helloInterface = getHelloInterface();
                        Call<MessageDto> call = helloInterface.getHello("Bearer " + token);
                        call.enqueue(new Callback<MessageDto>() {
                            @Override
                            public void onResponse(Call<MessageDto> call, Response<MessageDto> response) {
                                if(!response.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();
                                    Log.e("err ", response.message());
                                    return;
                                }
                                MessageDto dto = response.body();
                                helloText.setText(dto.getMessage());
                            }

                            @Override
                            public void onFailure(Call<MessageDto> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                                Log.e("err ", t.getMessage());
                            }
                        });
                    }
                });
    }

    public void signOut() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(HelloActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private HelloInterface getHelloInterface() {
        Retrofit retrofit =
                new Retrofit.Builder()
                .baseUrl("http://192.168.1.36:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HelloInterface helloInterface = retrofit.create(HelloInterface.class);
        return helloInterface;
    }
}