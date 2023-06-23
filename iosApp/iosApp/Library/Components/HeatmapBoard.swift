import SwiftUI
import shared

struct HeatmapBoard: View {
	let heatmap: SectorHeatmapDistribution
	
	var body: some View {
		GeometryReader { geometry in
			let center = CGPoint(x: geometry.size.width / 2, y: geometry.size.height / 2)
			let radius = geometry.size.width / 2

			ZStack {
				misses(center: center, radius: radius)
				doubles(center: center, radius: radius * 0.85, sectors: heatmap.getDoubles())
				outers(center: center, radius: radius * 0.75, sectors: heatmap.getOuters())
				triples(center: center, radius: radius * 0.45, sectors: heatmap.getTriples())
				inners(center: center, radius: radius * 0.35, sectors: heatmap.getInners())
				if let heat = heatmap.getBullseye() {
					bullseye(center: center, radius: radius * 0.1, heat: heat)
				}
				if let heat = heatmap.getDoubleBullseye() {
					doubleBullseye(center: center, radius: radius * 0.05, heat: heat)
				}
				DartsNumbers(center: center, radius: radius)
			}
		}
		.aspectRatio(1, contentMode: .fit)
	}
	
	@ViewBuilder
	private func misses(center: CGPoint, radius: CGFloat) -> some View {
		Arc(
			center: center,
			radius: radius,
			startAngle: .degrees(0),
			endAngle: .degrees(360),
			color: .black
		)
	}
	
	@ViewBuilder
	private func doubles(center: CGPoint, radius: CGFloat, sectors: [SectorHeat]) -> some View {
		sectorLevel(
			center: center,
			radius: radius,
			sectors: sectors
		)
	}
	
	@ViewBuilder
	private func outers(center: CGPoint, radius: CGFloat, sectors: [SectorHeat]) -> some View {
		sectorLevel(
			center: center,
			radius: radius,
			sectors: sectors
		)
	}
	
	@ViewBuilder
	private func triples(center: CGPoint, radius: CGFloat, sectors: [SectorHeat]) -> some View {
		sectorLevel(
			center: center,
			radius: radius,
			sectors: sectors
		)
	}
	
	@ViewBuilder
	private func inners(center: CGPoint, radius: CGFloat, sectors: [SectorHeat]) -> some View {
		sectorLevel(
			center: center,
			radius: radius,
			sectors: sectors
		)
	}
	
	@ViewBuilder
	private func bullseye(center: CGPoint, radius: CGFloat, heat: SectorHeat) -> some View {
		Arc(
			center: center,
			radius: radius,
			startAngle: .degrees(0),
			endAngle: .degrees(360),
			color: heat.color
		)
	}
	
	@ViewBuilder
	private func doubleBullseye(center: CGPoint, radius: CGFloat, heat: SectorHeat) -> some View {
		Arc(
			center: center,
			radius: radius,
			startAngle: .degrees(0),
			endAngle: .degrees(360),
			color: heat.color
		)
	}
	
	@ViewBuilder
	private func sectorLevel(
		center: CGPoint,
		radius: CGFloat,
		sectors: [SectorHeat]
	) -> some View {
		ZStack {
			ForEach(sectors) { heat in
				let index = heat.sector.sectorOrder() - 1
				let start: Double = Double(-81 + index * 18)
				let end: Double = Double(-99 + index * 18)
				Arc(
					center: center,
					radius: radius,
					startAngle: .degrees(start),
					endAngle: .degrees(end),
					color: heat.color
				)
			}
		}
	}
}
