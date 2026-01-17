@file:Suppress("ClassName")

package me.app.yummy.core.notify

enum class NotificationDuration {
    SHORT, LONG
}
expect fun showToast(message: String, duration: NotificationDuration = NotificationDuration.SHORT)
expect fun showAlert(
    title: String,
    message: String,
    duration: NotificationDuration = NotificationDuration.SHORT
)