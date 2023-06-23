import shared
import SwiftUI

extension Turn {
	var rowColor: Color {
		if isOverkill {
			return .red
		} else if leftAfter == 0 {
			return Color.secondary
		} else {
			return Color.clear
		}
	}
}

extension Turn: Identifiable {
	
}
