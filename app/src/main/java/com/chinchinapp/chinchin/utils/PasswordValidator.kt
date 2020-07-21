package com.chinchinapp.chinchin.utils

import java.util.regex.Matcher
import java.util.regex.Pattern

class PasswordValidator {

    private val pattern: Pattern
    private var matcher: Matcher? = null

    init {
        pattern = Pattern.compile(PASSWORD_PATTERN)
    }

    fun validate(password: String): Boolean {

        matcher = pattern.matcher(password)
        return matcher!!.matches()

    }

    companion object {

        private const val PASSWORD_PATTERN = "(?=.*[A-Z])(?=.*[@#\$%*./^&+=])(?=\\S+\$).{5,}"
    }

}