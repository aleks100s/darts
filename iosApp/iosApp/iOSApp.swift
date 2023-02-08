import SwiftUI
import shared

@main
struct iOSApp: App {
	private let appModule = AppModule()
	
	var body: some Scene {
		WindowGroup {
			ContentView(module: appModule)
		}
	}
}
