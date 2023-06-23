import SwiftUI
import shared

struct TurnItem: View {
	let turn: Turn
	let shotsLeft: Int
	let useTurnColors: Bool
	let onSelect: () -> Void
	
	var body: some View {
		HStack(spacing: 0) {
			ForEach(turn.shots) { shot in
				Cell(text: shot.sector.valueString())
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
		.contentShape(Rectangle())
		.onTapGesture {
			onSelect()
		}
		.background(useTurnColors ? turn.rowColor : .clear)
		.frame(maxWidth: .infinity)
	}
}
