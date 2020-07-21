package com.chinchinapp.chinchin.utils.manager

import android.content.Context
import com.chinchinapp.chinchin.utils.Constans
import com.squareup.moshi.Moshi

class SessionManager {

    companion object {
        private var INSTANCE: SessionManager? = null
        private const val SESSION_EXPIRE_TIME = "SessionExpireTime"
        private const val USER_HAS_LOGGED = "HasLoggedIn"

        fun getInstance(): SessionManager {
            if (INSTANCE == null)
                INSTANCE = SessionManager()

            return INSTANCE!!
        }
    }

    fun setToken(token: String?) {
        PreferenceManager.getInstance().putString(Constans.TOKEN, token!!)
    }

    fun getToken(): String? {
        return PreferenceManager.getInstance().getString(Constans.TOKEN, null)
    }


    fun setSessionExpireTime(value: Long){
        PreferenceManager.getInstance().putLong(SESSION_EXPIRE_TIME, value)
    }

    fun getSessionExpireTime() = PreferenceManager.getInstance().getLong(SESSION_EXPIRE_TIME)

    fun resetSession(){
        setSessionExpireTime(CustomCookieManager.NO_EXPIRATION_SET)
    }

    fun setProfilePhoto(url: String?) {
        PreferenceManager.getInstance().putString(Constans.URL, url!!)
    }

    fun getProfilePhoto(): String? {
        return PreferenceManager.getInstance().getString(Constans.URL, null)
    }

    fun setMerchantVerified(valor: Boolean?) {
        PreferenceManager.getInstance().putBoolean("valor", valor)
    }

    fun getMerchantVerified(): Boolean? {
        return PreferenceManager.getInstance().getBoolean("valor", false)
    }

    fun setCurrentUser(email: String?) {
        PreferenceManager.getInstance().putString("email", email!!)
    }

    fun getCurrentUser(): String? {
        return PreferenceManager.getInstance().getString("email", "developapps58@gmail.com")
    }
}