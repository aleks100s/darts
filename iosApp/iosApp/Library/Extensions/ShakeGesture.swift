import UIKit
import SwiftUI

extension View {
	func onShake(perform action: @escaping () -> Void) -> some View {
		modifier(DeviceShakeViewModifier(action: action))
	}
}

struct DeviceShakeViewModifier: ViewModifier {
	let action: () -> Void

	func body(content: Content) -> some View {
		content
			.onAppear()
			.onReceive(NotificationCenter.default.publisher(for: UIDevice.deviceDidShakeNotification)) { _ in
				action()
			}
	}
}

extension UIWindow {
	 open override func motionEnded(_ motion: UIEvent.EventSubtype, with event: UIEvent?) {
		if motion == .motionShake {
			NotificationCenter.default.post(name: UIDevice.deviceDidShakeNotification, object: nil)
		}
	 }
}

extension UIDevice {
	static let deviceDidShakeNotification = Notification.Name(rawValue: "deviceDidShakeNotification")
}
