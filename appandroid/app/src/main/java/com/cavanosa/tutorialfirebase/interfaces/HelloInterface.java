package com.cavanosa.tutorialfirebase.interfaces;

import com.cavanosa.tutorialfirebase.dto.MessageDto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface HelloInterface {

    @GET("hello")
    Call<MessageDto> getHello(@Header("Authorization") String token);
}
