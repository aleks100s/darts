import shared
import SwiftUI

internal extension Set {
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

extension Set: Identifiable {
	
}
