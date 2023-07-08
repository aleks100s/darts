import SwiftUI

struct LinkModifier: ViewModifier {
	func body(content: Content) -> some View {
		content
			.foregroundColor(Color.accentColor)
			.underline()
	}
}

extension Text {
	func linkStyle() -> some View {
		modifier(LinkModifier())
	}
}
