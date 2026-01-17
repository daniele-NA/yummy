package me.app.yummy.core.notify

import android.app.AlertDialog
import android.widget.Toast
import me.app.yummy.MainActivity
import me.app.yummy.R

actual fun showToast(
    message: String,
    duration: NotificationDuration
) {
    Toast.makeText(MainActivity.mainContext, message.trim(), Toast.LENGTH_SHORT).show()
}

actual fun showAlert(
    title: String,
    message: String,
    duration: NotificationDuration
) {
    val context = MainActivity.mainContext
    AlertDialog.Builder(context, R.style.Theme_App_Dialog).apply {
        setIcon(R.drawable.main_ic)
        setTitle(title.trim())
        setMessage(message.trim())
        setPositiveButton("OK", null)
    }.show()
}