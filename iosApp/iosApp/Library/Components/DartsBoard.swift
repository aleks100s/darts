import SwiftUI
import shared

internal struct DartsBoard: View {
	let highlightedSector: Sector
	
	private let hitColor = Color.cyan
	
	var body: some View {
		GeometryReader { geometry in
			let center = CGPoint(x: geometry.size.width / 2, y: geometry.size.height / 2)
			let radius = min(geometry.size.width, geometry.size.height) / 2

			ZStack {
				misses(center: center, radius: radius)
				doubles(center: center, radius: radius * 0.9)
				outers(center: center, radius: radius * 0.8)
				triples(center: center, radius: radius * 0.5)
				inners(center: center, radius: radius * 0.4)
				bullseye(center: center, radius: radius * 0.15)
				doubleBullseye(center: center, radius: radius * 0.05)
			}
		}
		.aspectRatio(1, contentMode: .fit)
	}
	
	@ViewBuilder
	private func misses(center: CGPoint, radius: CGFloat) -> some View {
		let color = highlightedSector == Sector.miss ? hitColor : .black
		Arc(
			center: center,
			radius: radius,
			startAngle: .degrees(0),
			endAngle: .degrees(360),
			color: color
		)
	}
	
	@ViewBuilder
	private func doubles(center: CGPoint, radius: CGFloat) -> some View {
		sectorLevel(
			center: center,
			radius: radius,
			firstColor: .green,
			secondColor: .red,
			sector: highlightedSector.isDouble() ? highlightedSector : nil
		)
	}
	
	@ViewBuilder
	private func outers(center: CGPoint, radius: CGFloat) -> some View {
		sectorLevel(
			center: center,
			radius: radius,
			firstColor: .white,
			secondColor: .black,
			sector: highlightedSector.isOuter() ? highlightedSector : nil
		)
	}
	
	@ViewBuilder
	private func triples(center: CGPoint, radius: CGFloat) -> some View {
		sectorLevel(
			center: center,
			radius: radius,
			firstColor: .green,
			secondColor: .red,
			sector: highlightedSector.isTriplet() ? highlightedSector : nil
		)
	}
	
	@ViewBuilder
	private func inners(center: CGPoint, radius: CGFloat) -> some View {
		sectorLevel(
			center: center,
			radius: radius,
			firstColor: .white,
			secondColor: .black,
			sector: highlightedSector.isInner() ? highlightedSector : nil
		)
	}
	
	@ViewBuilder
	private func bullseye(center: CGPoint, radius: CGFloat) -> some View {
		let color = highlightedSector == Sector.singlebullseye ? hitColor : .green
		Arc(
			center: center,
			radius: radius,
			startAngle: .degrees(0),
			endAngle: .degrees(360),
			color: color
		)
	}
	
	@ViewBuilder
	private func doubleBullseye(center: CGPoint, radius: CGFloat) -> some View {
		let color = highlightedSector == Sector.doublebullseye ? hitColor : .red
		Arc(
			center: center,
			radius: radius,
			startAngle: .degrees(0),
			endAngle: .degrees(360),
			color: color
		)
	}
	
	@ViewBuilder
	private func sectorLevel(
		center: CGPoint,
		radius: CGFloat,
		firstColor: Color,
		secondColor: Color,
		sector: Sector?
	) -> some View {
		ZStack {
			ForEach(1 ..< 21) { index in
				let isSelected = sector?.sectorOrder() == Int32(index)
				let index = index - 1
				let start: Double = Double(-81 + index * 18)
				let end: Double = Double(-99 + index * 18)
				let color = isSelected ? hitColor : index % 2 == 1 ? firstColor : secondColor
				Arc(
					center: center,
					radius: radius,
					startAngle: .degrees(start),
					endAngle: .degrees(end),
					color: color
				)
			}
		}
	}
}

internal struct DartsBoard_Preview: PreviewProvider {
	static var previews: some View {
		DartsBoard(highlightedSector: .singleinner20)
	}
}
