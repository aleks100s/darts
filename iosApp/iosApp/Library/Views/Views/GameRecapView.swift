import SwiftUI
import shared
import Charts

internal struct GameRecapView: View {
	let history: [PlayerHistory]
	let averageSets: [PlayerGameValue]
	let biggestSets: [PlayerGameValue]
	let smallestSets: [PlayerGameValue]
	let misses: [PlayerGameValue]
	let overkills: [PlayerGameValue]
	
	var body: some View {
		ScrollView {
			VStack {
				chart
				valuesBlock(values: averageSets, title: String(localized: "average_set_recap"))
				valuesBlock(values: biggestSets, title: String(localized: "biggest_sets"))
				valuesBlock(values: smallestSets, title: String(localized: "smallest_sets"))
				valuesBlock(values: misses, title: String(localized: "misses_count"))
				valuesBlock(values: overkills, title: String(localized: "overkill_count"))
			}
		}
		.background(Color.background)
	}
	
	@ViewBuilder private var chart: some View {
		Chart {
			let maxSize = history.max(by: { $0.turns.count < $1.turns.count })?.turns.count ?? 0
			
			ForEach(history, id: \.id) { playerHistory in
				
				let data = playerHistory.chartData(size: Int32(maxSize))
				ForEach(data.indices, id: \.self) { index in
					LineMark(
						x: .value("", index),
						y: .value("", data[index].floatValue)
					)
				}
				.foregroundStyle(by: .value("Player", playerHistory.id))
				.symbol(by: .value("Player", playerHistory.id))
			}
		}
		.frame(height: 300)
		.padding()
	}
	
	@ViewBuilder
	private func valuesBlock(values: [PlayerGameValue], title: String) -> some View {
		VStack {
			SectionHeader(title: title)
			ForEach(values) { value in
				item(name: value.player.name, value: value.value)
			}
		}
		.padding(.top, 32)
	}
	
	@ViewBuilder
	private func item(name: String, value: Int32) -> some View {
		HStack {
			Text(name)
			Spacer()
			Text("\(value)")
		}
		.padding(.horizontal, 16)
		.padding(.vertical, 4)
	}
}
