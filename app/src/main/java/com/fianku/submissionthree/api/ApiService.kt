package com.fianku.submissionthree.api

import com.fianku.submissionthree.Response.SearchUserResponse
import com.fianku.submissionthree.Response.UserResponse
import com.fianku.submissionthree.Response.UsersResponseItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    fun getUsers(): Call<List<UsersResponseItem>>
    @GET("search/users")
    fun getSearchUser(
        @Query("q") q: String
    ): Call<SearchUserResponse>
    @GET("users/{login}")
    fun getDetailUser(
        @Path("login") login: String
    ): Call<UserResponse>
    @GET("users/{login}/{follow}")
    fun getFollowersUser(
        @Path("login") login: String,
        @Path("follow") follow: String
    ): Call<List<UsersResponseItem>>
}