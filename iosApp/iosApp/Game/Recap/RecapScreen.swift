import SwiftUI
import shared

struct RecapScreen: View {
	let history: [PlayerHistory]
	let averageTurns: [PlayerGameValue]
	let biggestTurns: [PlayerGameValue]
	let smallestTurns: [PlayerGameValue]
	let misses: [PlayerGameValue]
	let overkills: [PlayerGameValue]
	let duration: GameDuration
	let numberOfTurns: Int32
	
	var body: some View {
		GameRecapView(
			history: history,
			averageTurns: averageTurns,
			biggestTurns: biggestTurns,
			smallestTurns: smallestTurns,
			misses: misses,
			overkills: overkills,
			duration: duration,
			numberOfTurns: numberOfTurns
		)
	}
}
