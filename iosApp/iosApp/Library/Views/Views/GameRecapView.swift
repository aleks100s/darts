import SwiftUI
import shared
import Charts

internal struct GameRecapView: View {
	let history: [PlayerHistory]
	let averageTurns: [PlayerGameValue]
	let biggestTurns: [PlayerGameValue]
	let smallestTurns: [PlayerGameValue]
	let misses: [PlayerGameValue]
	let overkills: [PlayerGameValue]
	let duration: GameDuration
	let numberOfTurns: Int32
	
	var body: some View {
		ScrollView {
			VStack {
				chart
				if !duration.isEmpty {
					gameDurationItem
				}
				numberOfTurnsItem
				valuesBlock(values: averageTurns, title: String(localized: "average_set_recap"))
				valuesBlock(values: biggestTurns, title: String(localized: "biggest_sets"))
				valuesBlock(values: smallestTurns, title: String(localized: "smallest_sets"))
				valuesBlock(values: misses, title: String(localized: "misses_count"))
				valuesBlock(values: overkills, title: String(localized: "overkill_count"))
			}
		}
		.background(Color.background)
	}
	
	@ViewBuilder
	private var chart: some View {
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
		.aspectRatio(1.0, contentMode: .fit)
		.padding()
	}
	
	@ViewBuilder
	private var gameDurationItem: some View {
		HStack {
			Spacer()
			Text("game_duration \(duration.minutes) \(duration.seconds)")
				.font(.caption)
		}
		.padding(.horizontal, 16)
	}
	
	@ViewBuilder
	private var numberOfTurnsItem: some View {
		HStack {
			Spacer()
			Text("number_of_turns \(numberOfTurns)")
				.font(.caption)
		}
		.padding(.horizontal, 16)
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
