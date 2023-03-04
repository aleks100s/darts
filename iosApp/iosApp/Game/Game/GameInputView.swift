import SwiftUI
import shared

internal struct GameInputView: View {
	let state: GameState
	let onInputClick: (Sector) -> ()
	let onPlayerClick: () -> ()
	
	var body: some View {
		VStack(spacing: 0) {
			header()
			
			hintRow
			
			ScrollView {
				inputTable
			}
		}
		.background(Color.background)
	}
	
	@ViewBuilder
	private func header() -> some View {
		let turn = state.getCurrentSet()
		let currentResults = state.currentResults()
		VStack(spacing: 0) {
			gamePlayers(results: currentResults, onClick: onPlayerClick)
			
			PlayerHistoryHeader()
			
			currentTurn(turn: turn)
		}
	}
	
	@ViewBuilder
	private func gamePlayers(
		results: [GamePlayerResult],
		onClick: @escaping () -> Void
	) -> some View {
		ScrollView(.horizontal) {
			HStack(spacing: 0) {
				ForEach(results) { result in
					gamePlayerItem(
						result: result,
						onClick: onClick
					)
				}
			}
		}
		.scrollIndicators(.hidden)
	}
	
	@ViewBuilder
	private func gamePlayerItem(
		result: GamePlayerResult,
		onClick: @escaping () -> Void
	) -> some View {
		let backgroundColor = result.isCurrentPlayer ? Color.secondary : Color.surface
		let textColor = result.isCurrentPlayer ? Color.onSecondary : Color.onSurface
		
		VStack(spacing: 8) {
			Text(result.player.name)
			Text("game_player_result \(result.result)")
		}
		.foregroundColor(textColor)
		.padding(16)
		.background(backgroundColor)
		.onTapGesture {
			onClick()
		}
	}
	
	@ViewBuilder
	private func currentTurn(turn: Set) -> some View {
		TurnItem(turn: turn, shotsLeft: Int(turn.shotsLeft()), onSelect: {})
	}
	
	@ViewBuilder
	private var hintRow: some View {
		let cells = [
			String(localized: "inner"),
			String(localized: "outer"),
			String(localized: "double_"),
			String(localized: "triplet")
		]
		Row(cells: cells, spacing: 0)
	}
	
	@ViewBuilder
	private var inputTable: some View {
		VStack(spacing: 2) {
			ForEach(Sector.companion.sectors) { sectors in
				InputRow(sectors: sectors, onInputClick: onInputClick)
			}
			.listRowSeparator(.hidden)
			.background(Color.background)
		}
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
		HStack(alignment: .center) {
			Text(sector.uiString())
				.foregroundColor(sector.textColor)
		}
		.padding(.vertical, 12)
		.frame(maxWidth: .infinity)
		.background(sector.backgroundColor)
		.onTapGesture {
			onInputClick()
		}
	}
}
