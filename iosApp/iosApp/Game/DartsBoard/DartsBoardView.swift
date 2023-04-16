import SwiftUI
import shared

internal struct DartsBoardView: View {
	let turn: Turn
		
	var body: some View {
		ScrollView(showsIndicators: false) {
			VStack(spacing: 32) {
				ForEach(turn.shots.filter { !$0.sector.isNone() }) { shot in
					dartsBoardItem(shot: shot)
				}
			}
		}
		.background(Color.surface)
	}
	
	@ViewBuilder
	private func dartsBoardItem(shot: Shot) -> some View {
		VStack(alignment: .center) {
			Text(shot.sector.inputString)
			DartsBoard(highlightedSector: shot.sector)
		}
	}
}
