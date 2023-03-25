import SwiftUI
import shared

internal struct SectorHeatmapView: View {
	@StateObject var viewModel: IOSSectorHeatmapViewModel
	
	var body: some View {
		content
			.onAppear {
				viewModel.startObserving()
			}
			.onDisappear {
				viewModel.dispose()
			}
	}
	
	@ViewBuilder
	var content: some View {
		if let distribution = viewModel.state.distribution {
			GeometryReader { geometry in
				let center = CGPoint(x: geometry.size.width / 2, y: geometry.size.height / 2)
				let radius = geometry.size.width / 2

				ZStack {
					misses(center: center, radius: radius)
					doubles(center: center, radius: radius * 0.9, sectors: distribution.getDoubles())
					outers(center: center, radius: radius * 0.8, sectors: distribution.getOuters())
					triples(center: center, radius: radius * 0.5, sectors: distribution.getTriples())
					inners(center: center, radius: radius * 0.4, sectors: distribution.getInners())
					if let heat = distribution.getBullseye() {
						bullseye(center: center, radius: radius * 0.15, heat: heat)
					}
					if let heat = distribution.getDoubleBullseye() {
						doubleBullseye(center: center, radius: radius * 0.05, heat: heat)
					}
				}
			}
		} else {
			NoDataView()
		}
	}
	
	@ViewBuilder
	private func misses(center: CGPoint, radius: CGFloat) -> some View {
		Arc(
			center: center,
			radius: radius,
			startAngle: .degrees(0),
			endAngle: .degrees(360),
			color: .darkGrey
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
