package com.example.mystory.api

import com.example.mystory.data.respon.AddStoryResponse
import com.example.mystory.data.respon.GetStoriesResponse
import com.example.mystory.data.login.LoginResponse
import com.example.mystory.data.respon.RegisterResponse
import com.example.mystory.data.respon.YourResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query


interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    
    
    @GET("stories")
    suspend fun getStories(@Header("Authorization") token: String): GetStoriesResponse

    @Multipart
    @POST("stories")
    suspend fun addStory(
        @Header("Authorization") Authorization: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): AddStoryResponse



    @GET("stories")
    suspend fun getStoriesForPaging(
        @Header("Authorization") authHeader: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): YourResponse

    @GET("stories")
    fun getUserLocation(
        @Header("Authorization") authHeader: String,
        @Query("location") location: Int
    ): Call<YourResponse>

}