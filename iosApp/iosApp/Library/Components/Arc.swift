import SwiftUI

internal struct Arc: View {
	let center: CGPoint
	let radius: CGFloat
	let startAngle: Angle
	let endAngle: Angle
	let color: Color

	var body: some View {
		Path { path in
			path.move(to: center)
			path.addArc(
				center: center,
				radius: radius,
				startAngle: startAngle,
				endAngle: endAngle,
				clockwise: true
			)
		}
		.fill(color)
	}
}
