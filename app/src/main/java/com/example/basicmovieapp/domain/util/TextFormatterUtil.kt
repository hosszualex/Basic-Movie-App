package com.example.basicmovieapp.domain.util

import java.text.DecimalFormat
import java.text.SimpleDateFormat

object TextFormatterUtil {
    private val yearOfReleaseFormat = SimpleDateFormat("yyyy")
    private val decimalFormat = DecimalFormat("#,###")
    fun getYearFromString(date: String): String {
        yearOfReleaseFormat.parse(date)?.let {
            return yearOfReleaseFormat.format(it)
        }
        return ""
    }

    fun formatRevenueAmount(value: Int): String {
        return decimalFormat.format(value).replace(",", ".")
    }

}