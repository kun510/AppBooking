package com.doancuoinam.hostelappdoancuoinam.Service;

import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.Boarding_host;
import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.ImgRoom;
import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.NotificationApp;
import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.Rent;
import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.Room;
import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.RoomFavourite;
import com.doancuoinam.hostelappdoancuoinam.Model.Request.LoginRequest;
import com.doancuoinam.hostelappdoancuoinam.Model.Request.RegisterRequest;
import com.doancuoinam.hostelappdoancuoinam.Model.Response.Response;
import com.doancuoinam.hostelappdoancuoinam.Model.Response.ResponseLogin;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {
    @POST("user/register")
    Call<Response> register(@Body RegisterRequest request);
    @POST("user/login")
    Call<ResponseLogin> login(@Body LoginRequest loginRequest);
    @GET("/user/getAllRoomByBoarding")
    Call<List<Room>> allRoomByBoarding(@Query("boardingId") long boardingId);

    @GET("/user/getallroomhot")
    Call<List<Room>> getAllRoomsHot();
    @GET("/user/search")
    Call<List<Room>> getAllRoomsSearch(@Query("address") String address,
                                       @Query("price") String price,
                                       @Query("area") String area,
                                       @Query("people") String people,
                                       @Query("type") String type);

    @PUT("/user/addtoken")
    Call<Response> addToken(
            @Query("token_device") String tokenDevice,
            @Query("userID") long userID
    );
    @GET("/user/getRoomFavourite")
    Call<List<RoomFavourite>> getAllRoomFavourite(@Query("idUser") long idUser);
    @GET("/user/getallboardingNear")
    Call<List<Boarding_host>> getAllBoarding(@Query("area") String area);
    @POST("/user/addRoomFavourite")
    Call<Response> addRoomFavourite(@Query("idRoom") long idRoom, @Query("idUser") long idUser);
    @DELETE("/user/deleteRoomFavourite")
    Call<Boolean> deleteRoomFavourite(@Query("idRoomFavourite") long idRoomFavourite, @Query("idUser") long idUser);
    @GET("/user/getMyRoom")
    Call<List<Rent>> getMyRoom(@Query("UserId") long userId);
    @GET("/user/getNotificationReceiver")
    Call<List<NotificationApp>> allNotificationReceiver(@Query("idUserReceiver") long idUserReceiver);
    @GET("/user/getImgInRoom")
    Call<List<ImgRoom>> getImgInRoom(@Query("idRoom") long idRoom);

    @GET("/user/getallroom")
    Call<List<Room>> getRoomMap();

    @Multipart
    @POST("/host/addImgRoom")
    Call<Response> addImgRoom(
            @Part("idRoom") RequestBody idRoom,
            @Part("hostId") RequestBody hostId,
            @Part MultipartBody.Part image1,
            @Part MultipartBody.Part image2,
            @Part MultipartBody.Part image3,
            @Part MultipartBody.Part image4,
            @Part MultipartBody.Part image5

    );
}