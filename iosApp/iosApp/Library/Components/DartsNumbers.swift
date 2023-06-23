import SwiftUI
import shared

struct DartsNumbers: View {
	let center: CGPoint
	let radius: CGFloat
	
	var body: some View {
		Canvas { context, size in
			let textSize = radius / 8
			let textOffset = radius * 0.925
			let numbers = Sector.companion.sectorNumbers.map { $0.intValue }
			for number in numbers {
				let index = numbers.firstIndex(of: number) ?? 0
				let text = Text(String(number))
					.font(.system(size: textSize))
					.foregroundColor(.white)
				let point = CGPoint(x: 0, y: textOffset)
					.applying(CGAffineTransform(rotationAngle: Double(index) * .pi / 10 - .pi))
				
				context.draw(text, at: CGPoint(x: point.x + center.x, y: point.y + center.y))
			}
		}
	}
}
