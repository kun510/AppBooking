package com.doancuoinam.hostelappdoancuoinam.Service;

import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.Boarding_host;
import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.ImgRoom;
import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.ListandCoutRoom;
import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.NotificationApp;
import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.NotificationMessaging;
import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.Rent;
import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.Room;
import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.RoomFavourite;
import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.TotalBill;
import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.Users;
import com.doancuoinam.hostelappdoancuoinam.Model.Request.LoginRequest;
import com.doancuoinam.hostelappdoancuoinam.Model.Request.RegisterRequest;
import com.doancuoinam.hostelappdoancuoinam.Model.Response.Response;
import com.doancuoinam.hostelappdoancuoinam.Model.Response.ResponseAll;
import com.doancuoinam.hostelappdoancuoinam.Model.Response.ResponseLogin;
import com.doancuoinam.hostelappdoancuoinam.Model.Response.ResponseToken;

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
    @GET("/user/getAllRoomByBoarding")
    Call<List<Room>> allRoomByBoarding(@Query("boardingId") long boardingId);

    @GET("/user/getallroomhot")
    Call<List<Room>> getAllRoomsHot();

    @GET("/user/search")
    Call<List<Room>> getAllRoomsSearch(@Query("price") Integer price,
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

    @GET("/user/getusercurrent")
    Call<List<Users>> getUserCurrent(@Query("idUser") long idUser);

    @GET("/user/getbill")
    Call<List<TotalBill>> getAllBill(@Query("idUser") long idUser);

    @GET("/user/getToken")
    Call<ResponseToken> getToken(@Query("userId") long userId);

    @GET("/host/RoomEmptyByBoarding")
    Call<List<ListandCoutRoom>> getRoomEmptyByBoarding(@Query("hostId") long hostId);

    @GET("/host/getBoaddingByHost")
    Call<List<Boarding_host>> getRoomInBoarding(@Query("hostId") long hostId);

    @GET("/host/getUserInRent")
    Call<List<Rent>> getUserInRent(@Query("hostId") long hostId);

    @GET("/host/RoomByHost")
    Call<List<Room>> getRoomByHost(@Query("hostId") long hostId);

    @GET("/host/AllRoomByHost")
    Call<List<Room>> AllRoomByHost(@Query("hostId") long hostId);

    @GET("/host/getRents")
    Call<List<Rent>> getRentByHostIdConfirm(@Query("hostId") long hostId);

    @POST("user/register")
    Call<Response> register(@Body RegisterRequest request);

    @POST("user/login")
    Call<ResponseLogin> login(@Body LoginRequest loginRequest);

    @POST("/user/addRoomFavourite")
    Call<Response> addRoomFavourite(@Query("idRoom") long idRoom, @Query("idUser") long idUser);

    @POST("/user/rent")
    Call<ResponseAll> rent(@Query("idRoom") long idRoom, @Query("idUser") long idUser, @Query("numberUserInRoom") int numberUserInRoom);

    @POST("/user/notification")
    Call<ResponseAll> sendNotification(@Body NotificationMessaging notificationMessaging);

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

    @Multipart
    @POST("/host/addBoardingHostel")
    Call<ResponseAll> addBoardingHostel(
            @Query("address") String address,
            @Query("area") String area,
            @Query("hostId") long hostId,
            @Part MultipartBody.Part imageFile
    );

    @Multipart
    @POST("/host/addRoom")
    Call<ResponseAll> addRoom(
            @Part MultipartBody.Part description,
            @Part MultipartBody.Part numberRoom,
            @Part MultipartBody.Part electricBill,
            @Part MultipartBody.Part waterBill,
            @Part MultipartBody.Part price,
            @Part MultipartBody.Part people,
            @Part MultipartBody.Part selectedType,
            @Query("boardingId") long boardingId,
            @Query("hostId") long hostId,
            @Part MultipartBody.Part imageFile
    );

    @Multipart
    @POST("/host/addBill")
    Call<ResponseAll> addBill(
            @Part("electricBill") int electricBill,
            @Part("CostsIncurred") int costsIncurred,
            @Part("rentId") long rentId,
            @Part("hostId") long hostId

    );

    @PUT("/host/addUserInRoomMobile")
    Call<ResponseAll> addUserInRoomMobile(
            @Query("roomId") long roomId,
            @Query("rentId") long rentId
    );
    @PUT("/host/cancelUserInRoomMobile")
    Call<ResponseAll> cancelUserInRoomMobile(
            @Query("roomId") long roomId,
            @Query("rentId") long rentId
    );
    @Multipart
    @PUT("/user/update")
    Call<Users> updateUser(
            @Query("id") long id,
            @Part MultipartBody.Part address,
            @Part MultipartBody.Part email,
            @Part MultipartBody.Part name,
            @Part MultipartBody.Part imageFile1
    );
}
