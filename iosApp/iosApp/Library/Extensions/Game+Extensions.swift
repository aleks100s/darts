import shared
import SwiftUI

extension Game: Identifiable {}

extension Game {
	var titleUIString: LocalizedStringKey {
		if winner == nil {
			return "training \(id?.int32Value ?? 0) \(getFinishDateString())"
		} else {
			return "game_title \(id?.int32Value ?? 0) \(getFinishDateString())"
		}
	}
}
