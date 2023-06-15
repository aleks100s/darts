import SwiftUI

struct CalculatorLockScreenWidgetView: View {
	var body: some View {
		ZStack {
			RingView()
			ViewThatFits(in: .horizontal) {
				Text("🧮").padding(.horizontal, 8)
				
				Text("🧮")
			}
		}
		.font(.system(size: 12))
		.widgetURL(.calculator)
		.accessibilityElement()
		.accessibilityLabel("calculator")
		.accessibilityAddTraits(.isButton)
	}
}
