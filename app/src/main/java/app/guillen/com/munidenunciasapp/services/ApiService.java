package app.guillen.com.munidenunciasapp;

import java.util.List;

import app.guillen.com.munidenunciasapp.models.Denuncia;
import app.guillen.com.munidenunciasapp.models.ResponseMessage;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by guillen on 30/10/17.
 */

public interface ApiService {

    String API_BASE_URL = "https://productos-api-jguillen276.c9users.io";

    @GET("api/v1/denuncias")
    Call<List<Denuncia>> getDenuncias();

    @FormUrlEncoded
    @POST("/api/v1/denuncias")
    Call<ResponseMessage> createDenuncia(@Field("titulo") String titulo,
                                         @Field("comentario") String comentario,
                                         @Field("latitud") String latitud,
                                         @Field("longitud") String longitud,
                                         @Field("users_id") String users_id);

    @FormUrlEncoded
    @POST("/api/v1/login")
    Call<ResponseMessage> validateUser(@Field("username") String username,
                                       @Field("password") String password);

    @Multipart
    @POST("/api/v1/denuncias")
    Call<ResponseMessage> createDenunciaWithImage(
            @Part("titulo") RequestBody titulo,
            @Part("comentario") RequestBody comentario,
            @Part("latitud") RequestBody latitud,
            @Part("longitud") RequestBody longitud,
            @Part MultipartBody.Part imagen,
            @Part("users_id") RequestBody users_id
            );

    @DELETE("/api/v1/denuncias/{id}")
    Call<ResponseMessage> destroyDenuncia(@Path("id") Integer id);

    @GET("api/v1/denuncias/{id}")
    Call<Denuncia> showDenuncia(@Path("id") Integer id);

}