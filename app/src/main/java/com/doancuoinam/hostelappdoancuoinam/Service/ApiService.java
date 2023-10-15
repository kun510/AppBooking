package com.doancuoinam.hostelappdoancuoinam.Service;

import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.Room;
import com.doancuoinam.hostelappdoancuoinam.Model.Request.LoginRequest;
import com.doancuoinam.hostelappdoancuoinam.Model.Request.RegisterRequest;
import com.doancuoinam.hostelappdoancuoinam.Model.Request.SearchRequest;
import com.doancuoinam.hostelappdoancuoinam.Model.Response.Response;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @POST("user/register")
    Call<Response> register(@Body RegisterRequest request);
    @POST("user/login")
    Call<Response> login(@Body LoginRequest loginRequest);
    @GET("/user/getallroom")
    Call<List<Room>> getAllRooms();
    @GET("/user/getallroomhot")
    Call<List<Room>> getAllRoomsHot();
    @GET("/user/search")
    Call<List<Room>> getAllRoomsSearch(@Query("address") String address,
                                       @Query("price") String price,
                                       @Query("area") String area,
                                       @Query("people") String people,
                                       @Query("type") String type);
}
