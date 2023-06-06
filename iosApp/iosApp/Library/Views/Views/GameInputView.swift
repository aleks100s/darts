import SwiftUI
import shared

internal struct GameInputView: View {
	let state: GameState
	let onInputClick: (Sector) -> ()
	let onPlayerClick: (Int) -> ()
	
	var body: some View {
		VStack(spacing: 0) {
			header
			hintRow
			inputTable
		}
	}
	
	@ViewBuilder
	private var header: some View {
		let turn = state.getCurrentTurn()
		let currentResults = state.currentResults()
		
		VStack(spacing: 0) {
			gamePlayers(results: currentResults, onClick: onPlayerClick)
			PlayerHistoryHeader()
			currentTurn(turn: turn)
		}
	}
	
	@ViewBuilder
	private var hintRow: some View {
		let cells = [
			String(localized: "single"),
			String(localized: "double_"),
			String(localized: "triplet")
		]
		Row(cells: cells, spacing: 0)
			.background(Color.surface)
	}
	
	@ViewBuilder
	private var inputTable: some View {
		ScrollView {
			VStack(spacing: 2) {
				ForEach(Sector.companion.sectors) { sectors in
					InputRow(sectors: sectors, onInputClick: onInputClick)
				}
				.listRowSeparator(.hidden)
			}
			.background(ScrollViewConfigurator {
				$0?.bounces = false
			})
		}
		.background(Color.background)
	}
	
	@ViewBuilder
	private func gamePlayers(
		results: [GamePlayerResult],
		onClick: @escaping (Int) -> Void
	) -> some View {
		GeometryReader { geometry in
			let width = geometry.size.width
			let itemWidth = results.count < 4 ? width / CGFloat(results.count) : width / 3
			
			ScrollView(.horizontal) {
				HStack(spacing: 0) {
					ForEach(results) { result in
						gamePlayerItem(
							result: result,
							itemWidth: itemWidth,
							onClick: { onClick(results.firstIndex(of: result) ?? 0) }
						)
					}
				}
			}
			.scrollIndicators(.hidden)
		}
		.frame(height: 80)
	}
	
	@ViewBuilder
	private func gamePlayerItem(
		result: GamePlayerResult,
		itemWidth: CGFloat,
		onClick: @escaping () -> Void
	) -> some View {
		let backgroundColor = result.isCurrentPlayer ? Color.secondary : Color.surface
		let textColor = result.isCurrentPlayer ? Color.onSecondary : Color.onSurface
		
		Button {
			onClick()
		} label: {
			VStack(spacing: 4) {
				Text(result.player.name)
				Text("game_player_result \(result.score)")
				if state.isStatisticsEnabled {
					Text("game_player_avg \(result.turnAverage) \(result.overallTurnAverage)")
						.font(.caption)
				}
			}
			.lineLimit(1)
			.padding(.horizontal, 12)
			.padding(.vertical, 8)
			.frame(minWidth: itemWidth, maxWidth: itemWidth)
			.background(backgroundColor)
		}
		.tint(textColor)
	}
	
	@ViewBuilder
	private func currentTurn(turn: Turn) -> some View {
		TurnItem(turn: turn, shotsLeft: Int(turn.shotsLeft()), onSelect: {})
			.background(Color.background)
	}
	
	@ViewBuilder
	private func InputRow(
		sectors: [Sector],
		onInputClick: @escaping (Sector) -> ()
	) -> some View {
		HStack(spacing: 2) {
			ForEach(sectors) { sector in
				InputCell(sector: sector) {
					onInputClick(sector)
				}
			}
			.background(Color.background)
		}
		.frame(maxWidth: .infinity)
	}
	
	@ViewBuilder
	private func InputCell(
		sector: Sector,
		onInputClick: @escaping () -> ()
	) -> some View {
		Button {
			onInputClick()
		} label: {
			HStack(alignment: .center) {
				Text(sector.inputString)
			}
			.padding(.vertical, 12)
			.frame(maxWidth: .infinity)
			.background(sector.backgroundColor)
		}
		.tint(sector.textColor)
	}
}

struct ScrollViewConfigurator: UIViewRepresentable {
	let configure: (UIScrollView?) -> ()
	func makeUIView(context: Context) -> UIView {
		let view = UIView()
		DispatchQueue.main.async {
			configure(view.enclosingScrollView())
		}
		return view
	}

	func updateUIView(_ uiView: UIView, context: Context) {}
}

extension UIView {
	func enclosingScrollView() -> UIScrollView? {
		var next: UIView? = self
		repeat {
			next = next?.superview
			if let scrollview = next as? UIScrollView {
				return scrollview
			}
		} while next != nil
		return nil
	}
}
