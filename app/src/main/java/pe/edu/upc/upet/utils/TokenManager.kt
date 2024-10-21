package pe.edu.upc.upet.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.auth0.jwt.JWT
import com.auth0.jwt.interfaces.DecodedJWT
import pe.edu.upc.upet.MyApplication

object TokenManager {
    private const val PREF_NAME = "my_app_prefs"
    private const val KEY_ACCESS_TOKEN = "access_token"

    private fun getSharedPreferences(): SharedPreferences {
        return MyApplication.getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveToken(token: String) {
        val sharedPreferences = getSharedPreferences()
        val editor = sharedPreferences.edit()
        editor.putString(KEY_ACCESS_TOKEN, token)
        Log.d("TokenManager", "Token saved: $token")
        editor.apply()
    }

    fun getToken(): String? {
        val sharedPreferences = getSharedPreferences()
        return sharedPreferences.getString(KEY_ACCESS_TOKEN, null)
    }

    fun clearToken() {
        val sharedPreferences = getSharedPreferences()
        val editor = sharedPreferences.edit()
        editor.remove(KEY_ACCESS_TOKEN)
        editor.apply()
        Log.d("TokenManager", "Token eliminado de SharedPreferences")

        // Verificación opcional para asegurarse de que el token ha sido eliminado
        val tokenAfterClear = sharedPreferences.getString(KEY_ACCESS_TOKEN, null)
        if (tokenAfterClear == null) {
            Log.d("TokenManager", "Token eliminado correctamente")
        } else {
            Log.e("TokenManager", "Error al eliminar el token")
        }
    }

    fun isUserAuthenticated(): Boolean {
        val token = getToken()
        return !token.isNullOrEmpty()
    }

    fun getUserIdAndRoleFromToken(): Triple<Int, String, Boolean>? {
        val token = getToken()
        Log.d("TokenManager", "Token: $token")
        return if (token != null) {
            try {
                // Decodifica el token
                val decodedJWT: DecodedJWT = JWT.decode(token)

                // Ahora puedes acceder a los datos del usuario
                val userId = decodedJWT.getClaim("user_id").asInt()
                val userRoleString = decodedJWT.getClaim("user_role").asString()
                val registered = decodedJWT.getClaim("registered").asBoolean()
                Log.d("TokenManager", "user_id: ${decodedJWT.getClaim("user_id").asInt()}")
                Log.d("TokenManager", "user_role: ${decodedJWT.getClaim("user_role").asString()}")
                Log.d("TokenManager", "registered: ${decodedJWT.getClaim("registered").asBoolean()}")

                Triple(userId, userRoleString, registered)
            } catch (error: Exception) {
                println("Error decodificando el token: $error")
                null
            }
        } else {
            println("No hay token disponible")
            null
        }
    }

    fun saveEmail(email: String): Boolean {
        val sharedPreferences = getSharedPreferences()
        val editor = sharedPreferences.edit()
        editor.putString("user_email", email)
        Log.d("TokenManager", "Email saved: $email")
        return editor.commit()
    }

    fun getEmail(): String? {
        val sharedPreferences = getSharedPreferences()
        return sharedPreferences.getString("user_email", null)
    }
}