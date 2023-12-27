package com.example.androidconcertapp.model

import android.util.Log
import com.auth0.android.jwt.JWT

private const val TAG = "User"
data class User(val idToken: String? = null, val accessToken: String? = null) {
    var id: String = ""
    var name: String = ""
    private var email: String = ""
    private var emailVerified: String = ""
    private var picture: String = ""
    private var updatedAt: String = ""

    init {
        if (idToken != null) {
            try {
                // Attempt to decode the ID token.
                val jwt = JWT(idToken)

                // The ID token is a valid JWT,
                // so extract information about the user from it.
                id = jwt.subject ?: ""
                name = jwt.getClaim("name").asString() ?: ""
                email = jwt.getClaim("email").asString() ?: ""
                emailVerified = jwt.getClaim("email_verified").asString() ?: ""
                picture = jwt.getClaim("picture").asString() ?: ""
                updatedAt = jwt.getClaim("updated_at").asString() ?: ""
            } catch (error: com.auth0.android.jwt.DecodeException) {
                // The ID token is NOT a valid JWT, so log the error
                // and leave the user properties as empty strings.
                Log.e(TAG, "Error occurred trying to decode JWT: $error ")
            }
        } else {
            // The User object was instantiated with a null value,
            // which means the user is being logged out.
            // The user properties will be set to empty strings.
            Log.d(TAG, "User is logged out - instantiating empty User object.")
        }
    }

    /**
     * Returns a string representation of the User.
     */
    override fun toString(): String {
        return "Id: $id, Name: $name, Email: $email, Email Verified: $emailVerified, Picture: $picture, Updated At: $updatedAt"
    }
}
