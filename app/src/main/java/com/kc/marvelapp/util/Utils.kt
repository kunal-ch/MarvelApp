package com.kc.marvelapp.util

import java.text.ParseException
import java.text.SimpleDateFormat

object Utils {
    fun getDate(input: String): String{
        var result = ""
        try {
            val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            val date = inputDateFormat.parse(input)
            val reqDateFormat = SimpleDateFormat("MMMM dd, yyyy")
            result = reqDateFormat.format(date)
        } catch (e: ParseException){
            e.printStackTrace()
        }
        return result
    }
}