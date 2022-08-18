package com.folliedimomi.sharedPrefrense
import android.content.Context
import android.content.SharedPreferences

open class Session(context: Context) {
    private var _privateMode = 0  // shared pref mode
    private val _prefName = "nandini_herbal_care" // Shared preferences file name
    private val _userId = "userId"
    private val _shopId = "shopId"
    private val _userName = "userName"
    private val _company = "company"
    private val _mobile = "mobile"
    private val _userEmail = "userEmail"
    private val _userImage = "userImage"
    private val _key = "userKey"
    private val _isServerLogin = "isServerLogin"
    private val _session = "customSession"
    private var pref: SharedPreferences
    private var editor: SharedPreferences.Editor
    private var _context: Context = context

    init {
        pref = _context.getSharedPreferences(_prefName, _privateMode)
        editor = pref.edit()
    }

    fun isServerLoggedIn(): Boolean {
        return pref.getBoolean(_isServerLogin, false)
    }

    fun setServerLoggedIn(isLogin: Boolean) {
        editor.putBoolean(_isServerLogin, isLogin)
        editor.commit()
    }

    fun logOut() {
        //editor.clear()
        setUserEmail("")
        setUserId("")
        setUserImage("")
        setUserName("")
        setSecureKey("")
        setShopId("")
        setServerLoggedIn(false)
        editor.commit()
    }

    fun getUserId(): String? {
        return pref.getString(_userId, "")
    }

    fun setUserId(id: String) {
        editor.putString(_userId, id)
        editor.commit()
    }

    fun getUserName(): String? {
        return pref.getString(_userName, "")
    }

    fun setUserName(id: String) {
        editor.putString(_userName, id)
        editor.commit()
    }

    fun getCompany(): String? {
        return pref.getString(_company, "")
    }

    fun setCompany(id: String) {
        editor.putString(_company, id)
        editor.commit()
    }

    fun getMobile(): String? {
        return pref.getString(_mobile, "")
    }

    fun setMobile(id: String) {
        editor.putString(_mobile, id)
        editor.commit()
    }

    fun getUserEmail(): String? {
        return pref.getString(_userEmail, "")
    }

    fun setUserEmail(id: String) {
        editor.putString(_userEmail, id)
        editor.commit()
    }

    fun getUserImage(): String? {
        return pref.getString(_userImage, "")
    }

    fun setUserImage(id: String) {
        editor.putString(_userImage, id)
        editor.commit()
    }

    fun getSecureKey(): String? {
        return pref.getString(_key, "")
    }

    fun setSecureKey(id: String) {
        editor.putString(_key, id)
        editor.commit()
    }

    fun getAppSession(): String? {
        return pref.getString(_session, "")
    }

    fun setAppSession(sessionID: String) {
        editor.putString(_session, sessionID)
        editor.commit()
    }

    fun getShopId(): String? {
        return pref.getString(_shopId, "")
    }

    fun setShopId(id: String) {
        editor.putString(_shopId, id)
        editor.commit()
    }
}