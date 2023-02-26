import SwiftUI
import shared

internal struct TurnItem: View {
	let turn: Set
	let shotsLeft: Int
	let onSelect: () -> Void
	
	var body: some View {
		HStack(spacing: 0) {
			ForEach(turn.shots) { shot in
				Cell(text: shot.sector.uiString())
			}
			if shotsLeft == 3 {
				ForEach(0 ..< 3) { _ in
					Cell(text: " ")
				}
			} else if shotsLeft == 2 {
				ForEach(0 ..< 2) { _ in
					Cell(text: " ")
				}
			} else if shotsLeft == 1 {
				ForEach(0 ..< 1) { _ in
					Cell(text: " ")
				}
			}

			Cell(text: "\(turn.score())")
			Cell(text: "\(turn.leftAfter)")
		}
		.frame(maxWidth: .infinity)
		.background(turn.rowColor)
		.onTapGesture {
			onSelect()
		}
	}
}
