package me.app.yummy.core.notify

import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreGraphics.CGRectMake
import platform.UIKit.NSTextAlignmentCenter
import platform.UIKit.UIAlertAction
import platform.UIKit.UIAlertActionStyleDefault
import platform.UIKit.UIAlertController
import platform.UIKit.UIAlertControllerStyleAlert
import platform.UIKit.UIApplication
import platform.UIKit.UIColor
import platform.UIKit.UILabel
import platform.UIKit.UIView
import platform.UIKit.UIViewAnimationOptionCurveEaseInOut
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue


// == SHOWING DIFFERENT TYPES OF ALERTS == //
@OptIn(ExperimentalForeignApi::class)
actual fun showToast(
    message: String,
    duration: NotificationDuration
) {
    val viewController = UIApplication.sharedApplication.keyWindow?.rootViewController
    if (viewController != null) {
        dispatch_async(dispatch_get_main_queue()) {
            val toastView = UIView(frame = CGRectMake(0.0, 0.0, 300.0, 50.0)).apply {
                backgroundColor = UIColor.blackColor()
                alpha = 0.7
                layer.cornerRadius = 10.0
                center = viewController.view.center
            }

            val messageLabel = UILabel(frame = toastView.bounds).apply {
                text = message
                textColor = UIColor.whiteColor()
                textAlignment = NSTextAlignmentCenter
                numberOfLines = 2
            }

            toastView.addSubview(messageLabel)
            viewController.view.addSubview(toastView)

            UIView.animateWithDuration(
                0.5,
                animations = { toastView.alpha = 1.0 },
                completion = {
                    UIView.animateWithDuration(
                        0.5,
                        delay = if (duration == NotificationDuration.LONG) 2.5 else 1.5,
                        options = UIViewAnimationOptionCurveEaseInOut,
                        animations = { toastView.alpha = 0.0 },
                        completion = { toastView.removeFromSuperview() }
                    )
                }
            )
        }
    }
}

actual fun showAlert(
    title: String,
    message: String,
    duration: NotificationDuration
) {
    val viewController = UIApplication.sharedApplication.keyWindow?.rootViewController
    val alertController = UIAlertController.alertControllerWithTitle(
        title = title,
        message = message,
        preferredStyle = UIAlertControllerStyleAlert
    )
    alertController.addAction(
        UIAlertAction.actionWithTitle("OK", UIAlertActionStyleDefault, null)
    )
    dispatch_async(dispatch_get_main_queue()) {
        viewController?.presentViewController(alertController, animated = true, completion = null)
    }
}