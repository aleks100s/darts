import SwiftUI
import shared

internal struct SectorHeatmapView: View {
	@StateObject var viewModel: IOSSectorHeatmapViewModel
	
	private var idiom : UIUserInterfaceIdiom { UIDevice.current.userInterfaceIdiom }

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
			ScrollView {
				VStack(alignment: .leading, spacing: 32) {
					heatmap(heatmap: distribution)
					countList(list: distribution.heatmap)
				}
			}
		} else {
			NoDataView()
		}
	}
	
	@ViewBuilder
	private func countList(list: [SectorHeat]) -> some View {
		VStack(alignment: .leading) {
			ForEach(list) { sectorHeat in
				sectorHeatItem(heat: sectorHeat)
			}
		}
		.frame(maxWidth: .infinity)
		.padding(.horizontal, 16)
	}
	
	@ViewBuilder
	private func sectorHeatItem(heat: SectorHeat) -> some View {
		HStack(alignment: .center) {
			Image(systemName: "square.fill")
				.foregroundColor(heat.color)
			Text("sector_count \(heat.sector.uiString()) \(heat.count)")
			Spacer()
		}
	}
	
	@ViewBuilder
	private func heatmap(heatmap: SectorHeatmapDistribution) -> some View {
		GeometryReader { geometry in
			let center = CGPoint(x: geometry.size.width / 2, y: geometry.size.height / 2)
			let radius = geometry.size.width / 2

			ZStack {
				misses(center: center, radius: radius)
				doubles(center: center, radius: radius * 0.9, sectors: heatmap.getDoubles())
				outers(center: center, radius: radius * 0.8, sectors: heatmap.getOuters())
				triples(center: center, radius: radius * 0.5, sectors: heatmap.getTriples())
				inners(center: center, radius: radius * 0.4, sectors: heatmap.getInners())
				if let heat = heatmap.getBullseye() {
					bullseye(center: center, radius: radius * 0.15, heat: heat)
				}
				if let heat = heatmap.getDoubleBullseye() {
					doubleBullseye(center: center, radius: radius * 0.05, heat: heat)
				}
			}
		}
		.frame(minHeight: idiom == .pad ? 800 : 400)
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
