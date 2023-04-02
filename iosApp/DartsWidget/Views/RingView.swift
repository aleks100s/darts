import SwiftUI

struct RingView: View {
	private let lineWidth: CGFloat = 8

	var body: some View {
		Circle()
			.trim(from: 0, to: 1)
			.stroke(style: StrokeStyle(lineWidth: lineWidth, lineCap: .round, lineJoin: .round))
			.foregroundColor(Color.green)
			.rotationEffect(Angle(degrees: 270))
			.animation(.linear(duration: 1), value: 1)
			.frame(minWidth: 44, maxWidth: 64)
	}
}
