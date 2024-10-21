package pe.edu.upc.upet.feature_auth.data.remote

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AuthService {
    @GET("users")
    fun getUsers(): Call<List<UserResponse>>

    @POST("auth/sign-in")
    fun signIn(
        @Body token: SignInRequest
    ): Call<SignInResponse>

    @POST("auth/sign-up")
    fun signUp(
        @Body user: UserRequest
    ): Call<SignInResponse>


    @GET("users/{user_id}")
    fun getUserById(
        @Path("user_id") userId: Int
    ): Call<UserResponse>

    @PUT("users/{role_id}")
    fun updateUser(
        @Path("role_id") roleId: Int,
        @Body request: UpdateUserRequest
    ): Call<UserResponse>
}