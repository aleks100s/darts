import SwiftUI
import shared

internal struct DartsBoardView: View {
	let turn: Set
	
	var body: some View {
		ScrollView {
			VStack(spacing: 32) {
				ForEach(turn.shots) { shot in
					dartsBoardItem(shot: shot)
				}
			}
		}
		.background(Color.background)
	}
	
	@ViewBuilder
	private func dartsBoardItem(shot: Shot) -> some View {
		VStack(alignment: .center) {
			Text(shot.sector.uiString())
			DartsBoard(highlightedSector: shot.sector)
				.padding()
		}
		.frame(minWidth: 400, minHeight: 400)
	}
}
