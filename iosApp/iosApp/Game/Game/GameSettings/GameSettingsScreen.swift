import SwiftUI
import shared

struct GameSettingsScreen: View {
	let gameSettings: GameSettings
	let onClose: () -> Void
	
	private let yes = "✅"
	private let no = "❌"
	
	var body: some View {
		VStack(alignment: .center, spacing: 16) {
			Text("game_settings")
				.font(.headline)
				.padding(16)
			
			LabeledContent("finish_with_doubles", value: gameSettings.isFinishWithDoublesChecked ? yes : no)
			LabeledContent("turn_limit", value: gameSettings.isTurnLimitEnabled ? yes : no)
			LabeledContent("randomize_player_order", value: gameSettings.isRandomPlayersOrderChecked ? yes : no)
			LabeledContent("enable_statistics", value: gameSettings.isStatisticsEnabled ? yes : no)
			
			Spacer()
			
			Button("return_to_game", action: onClose)
				.padding(16)
		}
		.padding(.horizontal, 16)
	}
}
