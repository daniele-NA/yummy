import UIKit
import SwiftUI
import YummyIos  // MODULE NAME ( see it into build.gradle file )
import UserNotifications

@main
struct YummyApp: App {
    
    init(){
        let center=UNUserNotificationCenter.current()

        // ASK FOR FULL NOTIFICATION PERMISSION //
        center.requestAuthorization(options: [.alert, .badge, .sound]) { granted, error in
            if let error = error {
                print("Errore: \(error)")
                DispatchQueue.main.async {
                    if let url = URL(string: UIApplication.openSettingsURLString) {
                        UIApplication.shared.open(url, options: [:], completionHandler: nil)
                    }
                }
                return
            }

            if granted {
                print("Permesso notifiche concesso")
            } else {
                DispatchQueue.main.async {
                    if let url = URL(string: UIApplication.openSettingsURLString) {
                        UIApplication.shared.open(url, options: [:], completionHandler: nil)
                    }
                }
            }
        }
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

struct ComposeView: UIViewControllerRepresentable {
    init(){
        Debug_iosKt.LOG(value: "INITIALIZING APP")
    }
    func makeUIViewController(context: Context) -> UIViewController {
        HelperKt.mainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView()
            .ignoresSafeArea()
    }
}
