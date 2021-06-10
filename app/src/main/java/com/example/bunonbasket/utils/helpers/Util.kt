package com.example.bunonbasket.utils.helpers

import java.util.regex.Pattern

/**
 * Created by inan on 9/6/21
 */
class Util {
    companion object {
        fun isPhoneValid(phone: String): Boolean {
            val expression = "^+[0-9]{10,13}$"
            val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
            val matcher = pattern.matcher(phone)
            return matcher.matches()
        }
    }
}