@file:JvmName("Utils")

package com.kisannetwork.contacts.sample.utils

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import org.ocpsoft.prettytime.PrettyTime
import java.util.*
import kotlin.random.Random


fun getRandomColor(context: Context): Int {
    val allColors =
        context.resources.getStringArray(com.kisannetwork.contacts.sample.R.array.colors)
    val randomIndex = Random(System.currentTimeMillis()).nextInt(allColors.size)
    return Color.parseColor(allColors[randomIndex])
}

fun generateOTP(len: Int): String {
    val numbers = "0123456789"
    val otp = CharArray(len)
    for (i in 0 until len) {
        otp[i] = numbers[Random.nextInt(numbers.length)]
    }
    return String(otp)
}

fun getHumanReadableDate(time: Long): String {
    if (time != 0L) {
        val p = PrettyTime(Locale.getDefault())
        return p.format(Date(time))
    }
    return "N/A"
}

fun Context.isOnline(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val netInfo = connectivityManager.activeNetworkInfo
    return netInfo != null && netInfo.isConnectedOrConnecting
}
