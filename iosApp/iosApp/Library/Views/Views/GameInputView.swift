import SwiftUI
import shared

struct GameInputView: View {
	let isStatisticsEnabled: Bool
	let currentTurn: Turn
	let currentResults: [GamePlayerResult]
	let onInputClick: (Sector) -> ()
	let onPlayerClick: (Int) -> ()
	
	var body: some View {
		VStack(spacing: 0) {
			header
			InputHintRowView()
			InputMatrixView(onInputClick: onInputClick)
		}
	}
	
	@ViewBuilder
	private var header: some View {
		VStack(spacing: 0) {
			gamePlayers(results: currentResults, onClick: onPlayerClick)
			PlayerHistoryHeader()
			currentTurn(turn: currentTurn)
		}
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
		.frame(height: isStatisticsEnabled ? 80 : 56)
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
				if isStatisticsEnabled {
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
		TurnItem(
			turn: turn,
			shotsLeft: Int(turn.shotsLeft()),
			useTurnColors: true,
			onSelect: {}
		)
			.background(Color.background)
	}
}
